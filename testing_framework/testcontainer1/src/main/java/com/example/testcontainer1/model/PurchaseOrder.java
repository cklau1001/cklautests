package com.example.testcontainer1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "PURCHASEORDER")
public class PurchaseOrder extends BaseEntity {

    @Id
    @Column(length = 128)
    private String orderId;

    @Column(length = 128)
    private String orderName;

    @Column(length = 512)
    private String orderDescription;

    /*
    Restrict the list of allowed status by Enum and define that in OrderStatus column
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(length = 128)
    private String customerId; // OneToOne

    /*
    purchaseOrder is the property in OrderItem entity class referencing Order entity class
     */
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> itemList = new ArrayList<>();

    /*
    Helper methods to add / remove orderItems
    To ensure that proper references are set up in both parent and child tables
     */
    public void addOrderItem(OrderItem orderItem) {
        itemList.add(orderItem);
        orderItem.setPurchaseOrder(this);
        log.info("[addOrderItem]: itemList size=" + itemList.size());
    }

    public void deleteOrderItem(OrderItem orderItem) {
        itemList.remove(orderItem);
        orderItem.setPurchaseOrder(null);
        log.info("[deleteOrderItem]: itemList size=" + itemList.size());
    }

    public PurchaseOrder(String orderId,  String orderName, String customerId, String orderDescription, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.customerId = customerId;
        this.orderDescription = orderDescription;
        this.orderStatus = orderStatus;
    }
}
