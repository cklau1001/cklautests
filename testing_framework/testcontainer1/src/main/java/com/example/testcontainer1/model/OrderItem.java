package com.example.testcontainer1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ORDERITEM")
public class OrderItem extends BaseEntity {

    @Id
    // @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 128)
    private String itemId;

    @Column(length = 256)
    private String itemName;

    private Integer itemQuantity;

    /*
    orderId is the property of Order Entity class that OrderItem has to reference ( i.e. foreign key )
     */
    @ManyToOne
    @JoinColumn(name = "orderId")
    private PurchaseOrder purchaseOrder;

    public OrderItem(String itemId, String itemName, int itemQuantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }


}
