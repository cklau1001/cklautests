package com.example.testcontainer1.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class OrderItemDto {

    public static String ITEMID = "ITEMID";
    public static String ITEMNAME = "ITEMNAME";
    public static String ITEMQUANTITY = "ITEMQUANTITY";

    private String itemId;

    private String itemName;

    private Integer itemQuantity;

    private String orderId;

    public OrderItemDto(String itemId, String itemName, Integer itemQuantity, String orderId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.orderId = orderId;
    }

    public OrderItemDto(Map<String, Object> map) {
        this.itemId = (String) map.get(ITEMID);
        this.itemName = (String) map.get(ITEMNAME);
        this.itemQuantity = (Integer) map.get(ITEMQUANTITY);
        this.orderId = (String) map.get(PurchaseOrderDto.ORDERID);

    }

}
