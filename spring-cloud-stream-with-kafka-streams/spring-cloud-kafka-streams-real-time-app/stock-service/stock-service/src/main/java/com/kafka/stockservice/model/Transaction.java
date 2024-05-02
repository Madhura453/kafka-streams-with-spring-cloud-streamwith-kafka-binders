package com.kafka.stockservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Data
public class Transaction {

    private Long id;
    private Long buyOrderId;
    private Long sellOrderId;
    private int quantity;
    private int price;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime creationTime;
    private String status;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", buyOrderId=" + buyOrderId +
                ", sellOrderId=" + sellOrderId +
                ", amount=" + quantity +
                ", price=" + price +
                ", creationTime=" + creationTime +
                ", status='" + status + '\'' +
                '}';
    }
}
