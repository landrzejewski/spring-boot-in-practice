# Zadanie: Rozproszona Saga - Provisioning infrastruktury

## 1. Cel ćwiczenia

Zaimplementować wzorzec **Saga (orchestration)** dla procesu provisioningu zasobu obliczeniowego, którego kroki są rozłożone na **dwie niezależne aplikacje Spring Boot** komunikujące się przez sieć. Po ukończeniu uczestnik powinien rozumieć i umieć pokazać w działaniu:

- kompensację jako akcję **semantyczną**, a nie techniczny rollback transakcji bazodanowej,
- kompensację przebiegającą w **odwrotnej kolejności** i **przez granicę usług** (rollback stanu w obu aplikacjach),
- **idempotentność** operacji i kompensacji w warunkach dostarczania *at-least-once*,
- **trwałość stanu** sagi i odtwarzanie (recovery) po awarii orkiestratora,
- **retry** kroków transient-failure oraz obsługę **timeoutów**.

---

## 2. Scenariusz domenowy

Budujemy uproszczony orkiestrator infrastruktury. Żądanie utworzenia instancji uruchamia sekwencję pięciu kroków. Każdy zasób jest **symulowany** (rekord w bazie + sztuczne opóźnienie) — nie integrujemy się z realną chmurą.

Pełny przebieg (happy path):

```
utwórz VM → podłącz wolumen → skonfiguruj sieć/VLAN → zarejestruj w service mesh → dodaj do monitoringu
```

Jeśli którykolwiek krok zawiedzie, system musi **wycofać** wszystkie kroki już wykonane — w odwrotnej kolejności i niezależnie od tego, w której aplikacji powstały.

---

## 3. Architektura - dwie aplikacje

Podział celowo przebiega **w środku** sagi, żeby kompensacja musiała cofnąć stan po obu stronach sieci.

```
                 POST /provisioning
                        │
                        ▼
        ┌───────────────────────────────┐         REST          ┌───────────────────────────────┐
        │   compute-service  (App 1)    │  ───────────────────► │   network-service  (App 2)    │
        │   :8081                       │                       │   :8082                       │
        │                               │                       │                               │
        │  • Orkiestrator Sagi          │   konfiguracja VLAN   │  • VLAN                        │
        │  • Trwały stan sagi (DB)      │   rejestracja w mesh  │  • Service mesh                │
        │  • Zasoby: VM, Wolumen        │   włączenie monitoru  │  • Monitoring                 │
        │  • Własna baza danych         │                       │  • Własna baza danych         │
        └───────────────────────────────┘                       └───────────────────────────────┘
              kroki 1–2 (lokalne)                                       kroki 3–5 (zdalne)
```

| Aplikacja | Odpowiedzialność |
|-----------|------------------|
| **compute-service** (App 1) | Punkt wejścia, **orkiestrator** sagi, trwały stan sagi we własnej bazie, operacje lokalne na VM i wolumenie + ich kompensacje. |
| **network-service** (App 2) | Operacje na VLAN, service mesh i monitoringu + ich kompensacje, własna baza, mechanizm wstrzykiwania błędów. |

Każda aplikacja ma **osobną bazę** (np. dwie instancje H2/Postgres). Nie ma współdzielonej transakcji bazodanowej — to założenie jest istotą zadania.

---

## 4. Kroki sagi i kompensacje

| # | Krok (akcja) | Aplikacja | Operacja | Kompensacja |
|---|--------------|-----------|----------|-------------|
| 1 | Utwórz VM | App 1 (lokalnie) | `createVm` | `deleteVm` |
| 2 | Podłącz wolumen | App 1 (lokalnie) | `attachVolume` | `detachVolume` |
| 3 | Skonfiguruj sieć/VLAN | App 2 (zdalnie) | `configureNetwork` | `releaseNetwork` |
| 4 | Zarejestruj w service mesh | App 2 (zdalnie) | `registerMesh` | `deregisterMesh` |
| 5 | Dodaj do monitoringu | App 2 (zdalnie) | `enableMonitoring` | `disableMonitoring` |

**Reguła kompensacji:** przy awarii na kroku *k* należy skompensować kroki *k‑1, k‑2, …, 1* w tej kolejności. Przykład — awaria na kroku 5 wymusza: `disableMonitoring` nie jest potrzebny (krok nieukończony) → `deregisterMesh` (App 2) → `releaseNetwork` (App 2) → `detachVolume` (App 1) → `deleteVm` (App 1).

---

## 5. Kontrakty API (wymagane minimum)

### compute-service (App 1, `:8081`)

**Rozpoczęcie sagi**
```
POST /api/v1/provisioning
Content-Type: application/json

{
  "instanceName": "edge-node-01",
  "vm":        { "cpu": 4, "ramGb": 16 },
  "volumeGb":  100,
  "networkSegment": "tenant-a-prod",
  "meshServiceName": "edge-node-01",
  "monitoringLabels": { "team": "platform", "env": "prod" },
  "failAtStep": null            // pole sterujące testem awarii, patrz §7
}

→ 202 Accepted
{ "sagaId": "9f1c…", "status": "STARTED" }
```

**Odczyt stanu sagi**
```
GET /api/v1/provisioning/{sagaId}

→ 200 OK
{
  "sagaId": "9f1c…",
  "status": "COMPLETED | COMPENSATED | FAILED | RUNNING | COMPENSATING",
  "steps": [
    { "name": "CREATE_VM",        "status": "DONE",        "resourceId": "vm-123" },
    { "name": "ATTACH_VOLUME",    "status": "DONE",        "resourceId": "vol-77" },
    { "name": "CONFIGURE_NETWORK","status": "COMPENSATED", "resourceId": null },
    ...
  ]
}
```

### network-service (App 2, `:8082`)

Każda operacja przyjmuje `sagaId` (korelacja) oraz nagłówek `Idempotency-Key`. Kompensacje są **idempotentne** i bezpieczne przy wielokrotnym wywołaniu.

```
POST   /api/v1/network/vlan          { sagaId, segment }        → { vlanResourceId }
DELETE /api/v1/network/vlan/{id}                                 → 204     (releaseNetwork)

POST   /api/v1/network/mesh          { sagaId, serviceName }    → { meshRegistrationId }
DELETE /api/v1/network/mesh/{id}                                 → 204     (deregisterMesh)

POST   /api/v1/network/monitoring    { sagaId, labels }         → { monitorId }
DELETE /api/v1/network/monitoring/{id}                           → 204     (disableMonitoring)
```

(Pomocniczo — do weryfikacji w testach akceptacyjnych — App 2 może wystawić `GET /api/v1/network/_state?sagaId=…` zwracające aktualnie istniejące zasoby danej sagi.)

---

## 6. Wymagania

**Funkcjonalne**
1. Happy path tworzy wszystkie 5 zasobów; status sagi → `COMPLETED`.
2. Awaria dowolnego kroku uruchamia kompensację wszystkich ukończonych kroków w odwrotnej kolejności, **w obu aplikacjach**; status → `COMPENSATED`.
3. Stan sagi (lista kroków + ich status + identyfikatory zasobów) jest **trwale persystowany** w bazie App 1 po każdej zmianie.

**Niefunkcjonalne / techniczne**
4. **Idempotentność**: ponowne dostarczenie tego samego wywołania operacji lub kompensacji (ten sam `Idempotency-Key`) nie powoduje podwójnego efektu (np. dwóch VLAN-ów ani błędu przy podwójnym usuwaniu).
5. **Korelacja**: `sagaId` jest propagowane do App 2 i widoczne w logach obu usług.
6. **Retry**: kroki oznaczone jako *transient-failure* są ponawiane (np. 3× z backoffem) zanim saga uzna je za nieudane.
7. **Timeout**: brak odpowiedzi z App 2 w zadanym czasie traktowany jest jak niepowodzenie kroku → kompensacja.
8. **Recovery**: po starcie App 1 wykrywa sagi w stanie nieterminalnym i je dokańcza lub kompensuje, bez ręcznej interwencji.

---

## 7. Wstrzykiwanie błędów (na potrzeby demonstracji)

Aby uczestnicy mogli wymusić kompensację w dowolnym punkcie, pole `failAtStep` w żądaniu początkowym przyjmuje nazwę kroku, który ma się celowo nie powieść, np.:

```
"failAtStep": "REGISTER_MESH"
```

Orkiestrator (lub odpowiednia usługa) przy dotarciu do tego kroku zgłasza wyjątek/zwraca błąd zamiast wykonać operację. Dla testu retry warto dodać też wariant „błąd przejściowy" (np. `failAtStep: "CONFIGURE_NETWORK"` + `failKind: "TRANSIENT"`), który powiedzie się po N próbach.

---

## 8. Kryteria akceptacji

| ID | Scenariusz | Oczekiwany rezultat |
|----|------------|---------------------|
| AC1 | `failAtStep = null` | Saga `COMPLETED`; w App 2 istnieją zasoby VLAN, mesh, monitoring; w App 1 — VM i wolumen. |
| AC2 | `failAtStep = CONFIGURE_NETWORK` | Skompensowane kroki 2 i 1 (App 1); w App 2 brak jakichkolwiek zasobów; saga `COMPENSATED`. |
| AC3 | `failAtStep = ADD_MONITORING` | Kompensacja: `deregisterMesh` → `releaseNetwork` (App 2), następnie `detachVolume` → `deleteVm` (App 1) — **kolejność odwrotna potwierdzona znacznikami czasu/logami**. |
| AC4 | Powtórne dostarczenie udanego wywołania kroku | Brak duplikatu zasobu (idempotentność operacji). |
| AC5 | Zabicie App 1 po kroku 3, restart | Saga zostaje wznowiona i kończy się stanem terminalnym bez ręcznej akcji (recovery). |
| AC6 | Powtórne wywołanie kompensacji | Brak błędu, brak podwójnego usunięcia (idempotentność kompensacji). |

