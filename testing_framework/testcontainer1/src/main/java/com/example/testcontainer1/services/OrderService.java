package com.example.testcontainer1.services;

import com.example.testcontainer1.model.OrderItem;
import com.example.testcontainer1.model.OrderStatus;
import com.example.testcontainer1.model.PurchaseOrder;
import com.example.testcontainer1.repository.OrderItemRepository;
import com.example.testcontainer1.repository.PurchaseOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final OrderItemRepository orderItemRepository;

    public void init(String orderId) {

        PurchaseOrder po = new PurchaseOrder(orderId, "order-1", "cust-1", "Order 1 desc", OrderStatus.QUEUED);
        OrderItem orderItem = new OrderItem("ITEM-1", "item-1", 1);
        po.addOrderItem(orderItem);
        purchaseOrderRepository.save(po);
    }

    public PurchaseOrder getOrderById(String orderId) throws EntityNotFoundException {

        Optional<PurchaseOrder> orderOptional = purchaseOrderRepository.findById(orderId);
        return orderOptional.orElseThrow(() -> new EntityNotFoundException(String.format("No such order=[%s]", orderId)));
    }

    public OrderItem getItemById(String itemId) throws EntityNotFoundException {
        Optional<OrderItem> itemOptional = orderItemRepository.findById(itemId);
        return itemOptional.orElseThrow(() -> new EntityNotFoundException(String.format("No such item=[%s]", itemId)));

    }
}
