package pl.fullstackdeveloper.payments.infrastructure.persistence;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "Card")
@Table(indexes = @Index(name = "card_number", columnList = "number"))
final class CardEntity {

    @Id
    private String id;
    @Column(unique = true)
    private String number;
    private LocalDate expiration;
    private String currencyCode;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String transactions;

    String getId() {
        return id;
    }

    void setId(final String id) {
        this.id = id;
    }

    String getNumber() {
        return number;
    }

    void setNumber(final String number) {
        this.number = number;
    }

    LocalDate getExpiration() {
        return expiration;
    }

    void setExpiration(final LocalDate expiration) {
        this.expiration = expiration;
    }

    String getCurrencyCode() {
        return currencyCode;
    }

    void setCurrencyCode(final String currencyCode) {
        this.currencyCode = currencyCode;
    }

    String getTransactions() {
        return transactions;
    }

    void setTransactions(final String transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        var otherEntity = (CardEntity) other;
        return Objects.equals(id, otherEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
