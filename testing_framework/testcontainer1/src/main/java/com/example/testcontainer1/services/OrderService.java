package com.example.testcontainer1.services;

import com.example.testcontainer1.dto.OrderItemDto;
import com.example.testcontainer1.dto.PurchaseOrderDto;
import com.example.testcontainer1.model.OrderItem;
import com.example.testcontainer1.model.OrderStatus;
import com.example.testcontainer1.model.PurchaseOrder;
import com.example.testcontainer1.repository.OrderItemRepository;
import com.example.testcontainer1.repository.PurchaseOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final OrderItemRepository orderItemRepository;

    public PurchaseOrderDto init(String orderId) {

        PurchaseOrder po = new PurchaseOrder(orderId, "order-1", "cust-1", "Order 1 desc", OrderStatus.QUEUED);
        OrderItem orderItem = new OrderItem(orderId + "_ITEM-1", "item-1", 1);
        po.addOrderItem(orderItem);
        purchaseOrderRepository.save(po);

        return getOrderById(orderId);

    }

    public PurchaseOrderDto getOrderById(String orderId) throws EntityNotFoundException {

        /*
        Optional<PurchaseOrder> orderOptional = purchaseOrderRepository.findById(orderId);
        return orderOptional.orElseThrow(() -> new EntityNotFoundException(String.format("No such order=[%s]", orderId)));
         */

        List<Map<String, Object>> resultset = purchaseOrderRepository.findByOrderIdAsDTOMap(orderId);
        if (resultset.isEmpty()) {
            throw new EntityNotFoundException(String.format("No such order=[%s]", orderId));
        }
        PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto(resultset.getFirst());
        resultset.forEach(rs -> {
            OrderItemDto orderItemDto = new OrderItemDto(rs);
            purchaseOrderDto.getOrderItemDtoList().add(orderItemDto);
        });

        return purchaseOrderDto;
    }

    public OrderItemDto getItemById(String itemId) throws EntityNotFoundException {
        /*
        Optional<OrderItem> itemOptional = orderItemRepository.findById(itemId);
        return itemOptional.orElseThrow(() -> new EntityNotFoundException(String.format("No such item=[%s]", itemId)));
         */

        Optional<OrderItemDto> orderItemDto = orderItemRepository.findByItemIdAsDTO(itemId);
        return orderItemDto.orElseThrow(() -> new EntityNotFoundException(String.format("No such item=[%s]", itemId)));
    }

    public void deleteAll() {
        purchaseOrderRepository.deleteAll();
    }
}
