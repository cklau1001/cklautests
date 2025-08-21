package com.example.testcontainer1.controller;

import com.example.testcontainer1.configuration.AppConstants;
import com.example.testcontainer1.dto.HeartBeatDto;
import com.example.testcontainer1.dto.OrderItemDto;
import com.example.testcontainer1.dto.PurchaseOrderDto;
import com.example.testcontainer1.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(AppConstants.CONTROLLER_URL_PREFIX)
@CrossOrigin(origins = "*", allowedHeaders = "Access-Control-Allow-Headers", exposedHeaders = "Allow-Control-Allow-Origin")
public class OrderController {

    private final OrderService orderService;

    @Operation(tags = "heartbeat", summary = "Give a heartbeat")
    @ApiResponse(responseCode = "200", description = "HeartBeat check", useReturnTypeSchema = true)
    @GetMapping(value = "/heartbeat")
    public ResponseEntity<HeartBeatDto> heartbeat(HttpServletRequest request) {

        String clientIp = request.getRemoteAddr();
        return ResponseEntity.ok(new HeartBeatDto("I am alive", clientIp, Date.from(Instant.now())));
    }

    @Operation(tags = "order", summary = "Initialize purchase order table")
    @ApiResponse(responseCode = "201", description = "Initialize purchase order table", useReturnTypeSchema = true)
    @PostMapping(AppConstants.ORDER_URI_PREFIX +  "/initOrders")
    public ResponseEntity<PurchaseOrderDto> initOrders() {

        PurchaseOrderDto purchaseOrderDto = orderService.init("PO-123-123");

        return ResponseEntity.created(URI.create("/order/id/" + purchaseOrderDto.getOrderId())).body(purchaseOrderDto);
    }

    @ApiResponse(responseCode = "200", description = "Get Purchase order by Order ID", useReturnTypeSchema = true)
    @Operation(tags = "order", summary = "Get Purchase order by order ID")
    @GetMapping(AppConstants.ORDER_URI_PREFIX + "/id/{orderId}")
    public ResponseEntity<PurchaseOrderDto> getOrderById(@PathVariable("orderId") String orderId) {

        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @ApiResponse(responseCode = "200", description = "Get order item by an item ID", useReturnTypeSchema = true)
    @Operation(tags = "orderItem", summary = "Get order item by an item ID")
    @GetMapping(AppConstants.ITEM_URI_PREFIX +  "/id/{itemId}")
    public ResponseEntity<OrderItemDto> getItemById(@PathVariable("itemId") String itemId) {

        return ResponseEntity.ok(orderService.getItemById(itemId));
    }

}
