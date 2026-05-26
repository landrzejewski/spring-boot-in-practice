package pl.training.sagademo.web;

import java.math.BigDecimal;

public record OrderRequest(
        String productId,
        int quantity,
        BigDecimal amount,
        String accountId,
        String address
) {}
