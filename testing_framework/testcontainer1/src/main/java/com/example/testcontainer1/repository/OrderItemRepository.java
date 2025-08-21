package com.example.testcontainer1.repository;

import com.example.testcontainer1.dto.OrderItemDto;
import com.example.testcontainer1.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

    @Query("SELECT new com.example.testcontainer1.dto.OrderItemDto(o.itemId, o.itemName, o.itemQuantity, o.purchaseOrder.orderId) " +
            "FROM OrderItem o WHERE o.itemId = :itemId")
    Optional<OrderItemDto> findByItemIdAsDTO(@Param("itemId") String itemId);

}
