package com.kafka.stockservice.controller;

import com.kafka.stockservice.model.TransactionTotal;
import com.kafka.stockservice.service.TransactionService;
import org.apache.kafka.streams.kstream.Windowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/all")
    public TransactionTotal getAllTransactionsSummary()
    {
        return transactionService.getAllTransactionsSummary();
    }

    @GetMapping("/product/{productId}")
    public TransactionTotal getSummaryByProductId(@PathVariable("productId") Integer productId)
    {
      return transactionService.getSummaryByProductId(productId);
    }

    @GetMapping("/product/latest/{productId}")
    public List<TransactionTotal> getLatestSummaryByProductId(@PathVariable("productId") Integer productId)
    {
        return transactionService.getLatestSummaryByProductId(productId);
    }


    @GetMapping("/product")
    public Map<Integer, TransactionTotal> getSummaryByAllProducts()
    {
       return transactionService.getSummaryByAllProducts();
    }

    @GetMapping("/latest/product")
    public Map<Windowed<Integer>, TransactionTotal> getLatestSummaryByAllProducts()
    {
        return transactionService.getLatestSummaryByAllProducts();
    }
}
