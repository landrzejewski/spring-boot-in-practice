package pl.fullstackdeveloper.payments.cqrs.queries.readmodel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "cards")
public class CardDocument {

    @Id
    private UUID id;
    private String number;
    private List<String> transactions;

    public CardDocument() {
    }

    public CardDocument(UUID id, String number) {
        this.id = id;
        this.number = number;
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(String transaction) {
        transactions.add(transaction);
    }

    public String getNumber() {
        return number;
    }

    public List<String> getTransactions() {
        return transactions;
    }

}
