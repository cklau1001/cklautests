package com.example.testcontainer1.unit;

import com.example.testcontainer1.dto.PurchaseOrderDto;
import com.example.testcontainer1.model.PurchaseOrder;
import com.example.testcontainer1.repository.OrderItemRepository;
import com.example.testcontainer1.repository.PurchaseOrderRepository;
import com.example.testcontainer1.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/*
    A simple example to perform unit testing on OrderService component using Mock and Captor that simplify result assertion.
    Pros:
       The persistence layer is mocked that takes out the database dependency during development.
    Cons:
       Results can only serve as a reference of performing expected behavior. Further testing with a database is needed
       for the sake of comprehensiveness. Any mocking may not be able to reflect the real environment.
 */

@Slf4j
@ExtendWith(MockitoExtension.class)
class OrderServiceUnitTests {

    private final String orderId = "PO-123-123";

    @InjectMocks     // target under test
    OrderService orderService;

    @Mock
    PurchaseOrderRepository mockPurchaseOrderRepository;

    @Mock
    OrderItemRepository mockOrderItemRepository;

    @Captor
    private ArgumentCaptor<PurchaseOrder> purchaseOrderArgumentCaptor;

    @Test
    void testInitSuccess() {

        when(mockPurchaseOrderRepository.findByOrderIdAsDTOMap(anyString())).thenReturn(List.of(new HashMap<>()));
        orderService.init(orderId);
        verify(mockPurchaseOrderRepository, times(1)).save(purchaseOrderArgumentCaptor.capture());

        log.info("orderId={}", purchaseOrderArgumentCaptor.getValue().getOrderId());
        assertThat(purchaseOrderArgumentCaptor.getValue().getOrderId()).isEqualTo(orderId);

    }

    @Test
    void testGetOrderByIdFailed() {

        when(mockPurchaseOrderRepository.findByOrderIdAsDTOMap(anyString())).thenReturn(List.of());
        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> orderService.getOrderById("INVALID_ORDER_ID")
        );

        log.info("[testGetOrderByIdFailed]: error={}", thrown.getMessage());
        assertThat(thrown.getMessage()).startsWith("No such order=");
    }

    @Test
    void testGetItemByIdFailed() {

        when(mockOrderItemRepository.findByItemIdAsDTO(anyString())).thenReturn(Optional.empty());
        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> orderService.getItemById("INVALID_ITEM_ID")
        );

        log.info("[testGetItemByIdFailed]: error={}", thrown.getMessage());
        assertThat(thrown.getMessage()).startsWith("No such item=");

    }

}
