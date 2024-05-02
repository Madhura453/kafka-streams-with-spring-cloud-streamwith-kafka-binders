package com.kafka.stockservice.logic;

import com.kafka.stockservice.model.Order;
import com.kafka.stockservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderLogic {

    private OrderRepository orderRepository;

    public OrderLogic(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    public Order orderSave(Order order)
    {
        return orderRepository.save(order);
    }

    @Transactional
    public boolean performUpdate(Long buyOrderId, Long sellOrderId, int count) {
        Order buyOrder = orderRepository.findById(buyOrderId).orElseThrow();

        Order sellOrder = orderRepository.findById(sellOrderId).orElseThrow();

        int buyAvailableCount = buyOrder.getProductCount() - buyOrder.getRealizedCount();

        int sellAvailableCount = sellOrder.getProductCount() - sellOrder.getRealizedCount();

        if(buyAvailableCount>=count && sellAvailableCount>=count)
        {
            buyOrder.setRealizedCount(buyAvailableCount);
            sellOrder.setRealizedCount(sellAvailableCount);
            orderRepository.save(buyOrder);
            orderRepository.save(sellOrder);
            return true;
        }
        return false;
    }
}
