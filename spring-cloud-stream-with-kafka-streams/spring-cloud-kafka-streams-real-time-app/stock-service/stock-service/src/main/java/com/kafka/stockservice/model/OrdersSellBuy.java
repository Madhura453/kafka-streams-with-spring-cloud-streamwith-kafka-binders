package com.kafka.stockservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Data
public class OrdersSellBuy {

    private int sellCount;
    private int buyCount;

    @Override
    public String toString() {
        return "OrdersSellBuy{" +
                "sellCount=" + sellCount +
                ", buyCount=" + buyCount +
                '}';
    }
}
