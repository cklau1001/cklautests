package com.example.testcontainer1.unit;

import com.example.testcontainer1.configuration.AppConstants;
import com.example.testcontainer1.controller.OrderController;
import com.example.testcontainer1.dto.OrderItemDto;
import com.example.testcontainer1.dto.PurchaseOrderDto;
import com.example.testcontainer1.exception.AppErrorResponse;
import com.example.testcontainer1.model.PurchaseOrder;
import com.example.testcontainer1.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = OrderController.class)
class OrderControllerUnitTests {

    private final static String orderId = "PO-123-123";
    private final static String itemId = "ITEM-1";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService mockOrderService;

    @Test
    void testHeartBeatSuccess() throws Exception {

        mockMvc.perform(get(AppConstants.CONTROLLER_URL_PREFIX + "/heartbeat").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("I am alive")));
    }

    @Test
    void testPurchaseOrderInitSuccess() throws Exception {

        PurchaseOrderDto mockPurchaseOrderDto = new PurchaseOrderDto();
        mockPurchaseOrderDto.setOrderId(orderId);

        when(mockOrderService.init(anyString())).thenReturn(mockPurchaseOrderDto);
        mockMvc.perform(post(AppConstants.ORDER_URL_PREFIX + "/initOrders").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void testInitFailed() throws Exception {
        when(mockOrderService.init(anyString())).thenThrow(new RuntimeException("Mocked exception"));
        mockMvc.perform(post(AppConstants.ORDER_URL_PREFIX + "/initOrders").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

    }

    @Test
    void getOrderByIdSuccess() throws Exception {
        PurchaseOrderDto mockPurchaseOrderDto = new PurchaseOrderDto();
        mockPurchaseOrderDto.setOrderId(orderId);

        when(mockOrderService.getOrderById(orderId)).thenReturn(mockPurchaseOrderDto);
        mockMvc.perform(get(AppConstants.ORDER_URL_PREFIX + "/id/" + orderId ).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId", is(orderId)));
    }

    @Test
    void getOrderByIdFailed() throws Exception {

        when(mockOrderService.getOrderById(anyString())).thenThrow(new EntityNotFoundException("Mocked exception"));
        mockMvc.perform(get(AppConstants.ORDER_URL_PREFIX + "/id/INVALID_ORDER_ID"  ).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode.errorCodeName",
                        is(AppErrorResponse.ErrorCodeConstants.RECORD_NOT_FOUND.getErrorCodeName())));

    }

    @Test
    void getItemByIdSuccess() throws Exception {

        OrderItemDto mockOrderItemDto = new OrderItemDto();
        mockOrderItemDto.setItemId(itemId);

        when(mockOrderService.getItemById(itemId)).thenReturn(mockOrderItemDto);
        mockMvc.perform(get(AppConstants.ITEM_URL_PREFIX + "/id/" + itemId ).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId", is(itemId)));

    }

    @Test
    void getItemByIdFailed() throws Exception {

        when(mockOrderService.getItemById(anyString())).thenThrow(new EntityNotFoundException("Mocked exception"));
        mockMvc.perform(get(AppConstants.ITEM_URL_PREFIX + "/id/" + itemId ).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode.errorCodeName",
                        is(AppErrorResponse.ErrorCodeConstants.RECORD_NOT_FOUND.getErrorCodeName())));


    }

}
