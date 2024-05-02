package com.kafka.stockservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Data
public class TransactionTotalWithProduct {
    private Transaction transaction;
    private Integer productId;

    @Override
    public String toString() {
        return "TransactionTotalWithProduct{" +
                "transaction=" + transaction +
                ", productId=" + productId +
                '}';
    }
}
