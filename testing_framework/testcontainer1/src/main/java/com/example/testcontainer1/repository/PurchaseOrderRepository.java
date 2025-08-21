package com.example.testcontainer1.repository;

import com.example.testcontainer1.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, String> {

    Optional<PurchaseOrder> findByOrderName(String orderName);

    @Query("SELECT o.orderId as ORDERID, o.customerId as CUSTOMERID, o.createdDate as ORDERDATE, " +
            "o.orderDescription as ORDERDESCRIPTION, o.orderStatus as ORDERSTATUS, " +
            "i.itemId as ITEMID, i.itemName as ITEMNAME, i.itemQuantity as ITEMQUANTITY " +
            "FROM PurchaseOrder o JOIN OrderItem i on o.orderId = i.purchaseOrder.orderId WHERE o.orderId = :orderId")
    List<Map<String,Object>> findByOrderIdAsDTOMap(@Param("orderId") String orderId);

}
