package com.example.testcontainer1.dto;

import com.example.testcontainer1.model.OrderStatus;
import com.example.testcontainer1.model.PurchaseOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
public class PurchaseOrderDto {

    public static final String ORDERID = "ORDERID";
    public static final String CUSTOMERID = "CUSTOMERID";
    public static final String ORDERDATE = "ORDERDATE";
    public static final String ORDERDESCRIPTION = "ORDERDESCRIPTION";
    public static final String ORDERSTATUS = "ORDERSTATUS";

    private String orderId;

    private String customerId;

    private Date createdDate;

    private String orderDescription;

    // data type of each property must be the same as the corresponding entity class
    private OrderStatus orderStatus;

    /*
       final is added so that AllArgConstructor skip to add this into the constructor
     */

    private final List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    public PurchaseOrderDto() {}
    /* Support Map-based constructor
       re-use same DTO with all or some fields from the target query
    */
    public PurchaseOrderDto(Map<String, Object> map) {
        this.orderId = (String) map.get(ORDERID);
        this.customerId = (String) map.get(CUSTOMERID);
        this.createdDate = (Date) map.get(ORDERDATE);
        this.orderDescription = (String) map.get(ORDERDESCRIPTION);
        this.orderStatus = (OrderStatus) map.get(ORDERSTATUS);

    }
}
