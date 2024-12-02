package pl.fullstackdeveloper.payments.domain;

import java.util.UUID;

import static java.util.UUID.randomUUID;

public record CardId(UUID value) {

    public CardId() {
        this(randomUUID());
    }

}
