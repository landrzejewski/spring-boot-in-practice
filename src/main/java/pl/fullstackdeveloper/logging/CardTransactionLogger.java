package pl.fullstackdeveloper.logging;

import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import pl.fullstackdeveloper.payments.TransactionAdded;
import java.util.logging.Logger;

@Component
public class CardTransactionLogger {

    private static final Logger LOGGER = Logger.getLogger(CardTransactionLogger.class.getName());

    /*

    Standardowy @EventListener działa synchronicznie i w tej samej transakcji co publikujący. To oznacza, że side-effect integracji jest częścią oryginalnej jednostki pracy — porażka listenera wywraca biznesową transakcję, a połączenie do bazy jest trzymane dłużej. Modulith celuje w rozdzielenie tych dwóch światów.
    Warstwa Spring Core: @TransactionalEventListener
    Bazowa adnotacja, na której Modulith buduje. Kluczowy jest atrybut phase:

    AFTER_COMMIT (domyślny) — listener uruchamia się dopiero po pomyślnym commit transakcji publikującej
    BEFORE_COMMIT
    AFTER_ROLLBACK
    AFTER_COMPLETION

    Sam @TransactionalEventListener nie uruchamia własnej transakcji — reaguje tylko na fazę istniejącej. Żeby listener działał w swojej transakcji, trzeba go dodatkowo opatrzyć @Transactional. Dokumentacja Modulith pokazuje ten „pełny" zestaw jako rekomendowany wzorzec integracji między modułami:
    java@Component
    class InventoryManagement {
        @Async
        @Transactional(propagation = Propagation.REQUIRES_NEW)
        @TransactionalEventListener
        void on(OrderCompleted event) {  }
    }

    Logika tej trójki: oryginalna transakcja biznesowa kończy się pomyślnie, a integracja asynchronicznie wykonuje się we własnej transakcji, by maksymalnie odsprzęgnąć integrację od oryginalnej jednostki pracy. REQUIRES_NEW jest tu istotne — gwarantuje świeżą, niezależną transakcję, a @Async przenosi wykonanie poza wątek publikujący. Spring
    Skrót Modulith: @ApplicationModuleListener
    To meta-adnotacja, która opakowuje async + nową transakcję + event listener w jedno. Jest dokładnym odpowiednikiem trójki powyżej: Garstecki
    java@Service
    class InventoryManagement {
        @ApplicationModuleListener
        void on(OrderCompleted event) { }
    }

    Adnotacja udostępnia atrybuty (przez @AliasFor), którymi możesz dostroić zachowanie:
        propagation — propagacja transakcji (aliasowane do @Transactional)
        readOnlyTransaction / readOnly — czy transakcja ma być read-only (domyślnie false)
        id — opcjonalny identyfikator listenera, domyślnie pełna sygnatura metody Spring
        condition — wyrażenie SpEL warunkujące obsługę eventu; domyślnie pusty string, czyli event obsługiwany zawsze Spring
    */




   /* @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)*/

    @ApplicationModuleListener
    public void onEvent(final TransactionAdded transactionAdded) throws InterruptedException {
        LOGGER.info("### New event: " + transactionAdded.toString());
        Thread.sleep(2_000);
        LOGGER.info("### After event: " + transactionAdded.toString());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onError(final TransactionAdded transactionAdded) throws InterruptedException {
        LOGGER.info("### Error: " + transactionAdded.toString());
    }

}
