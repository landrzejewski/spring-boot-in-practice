package pl.fullstackdeveloper.payments.cqrs.addtransaction;

public class AddTransactionResult {

    private String transactionId;

    public AddTransactionResult(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

}
