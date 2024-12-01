API Requirements/UseCases
- Card addCard(Currency currency)
- void addTransaction(CardNumber cardNumber, Money value, TransactionType transactionType) -> emits event: TransactionAdded 
- getCards(CardRepository cardRepository)
- getCard(CardRepository cardRepository)
