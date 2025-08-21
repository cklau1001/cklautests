package com.example.testcontainer1.integration;

import com.example.testcontainer1.configuration.AppConstants;
import com.example.testcontainer1.exception.AppErrorResponse;
import com.example.testcontainer1.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.security.SecureRandom;
import java.util.function.Supplier;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    private final String orderId = "PO-123-123";
    private final String itemId = orderId + "_ITEM-1";

    private static final Supplier<String> randomStringGenerator = () -> {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final int strLen = 8;
        SecureRandom sr = new SecureRandom();
        return sr.ints(strLen, 0, characters.length())
                .mapToObj(characters::charAt)
                .collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
                .toString();

    };


    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb1")
            .withUsername("postgres")
            .withPassword(randomStringGenerator.get());

    @BeforeAll
    static void setup() {

        postgreSQLContainer.start();
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();

        log.info("[setup]: PostgresSQL container started, jdbcUrl={}, username={}", jdbcUrl, username);
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
        log.info("[tearDown]: PostgreSQL container stopped");
    }

    @Autowired
    OrderService orderService;

    @BeforeEach
    void setupEach() {
        orderService.deleteAll();
        log.info("[setupEach]: Purchase Order tables cleaned up");
    }

    @Test
    void testPurchaseOrderInitSuccess() throws Exception {

        mockMvc.perform(post(AppConstants.ORDER_URL_PREFIX + "/initOrders").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
   }

    @Test
    void getOrderByIdSuccess() throws Exception {

        orderService.init(orderId);
        mockMvc.perform(get(AppConstants.ORDER_URL_PREFIX + "/id/" + orderId ).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId", is(orderId)));
    }

    @Test
    void getOrderByIdFailed() throws Exception {

        mockMvc.perform(get(AppConstants.ORDER_URL_PREFIX + "/id/INVALID_ORDER_ID"  ).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode.errorCodeName",
                        is(AppErrorResponse.ErrorCodeConstants.RECORD_NOT_FOUND.getErrorCodeName())));

    }

    @Test
    void getItemByIdSuccess() throws Exception {

        orderService.init(orderId);
        mockMvc.perform(get(AppConstants.ITEM_URL_PREFIX + "/id/" + itemId ).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId", is(itemId)));

    }

    @Test
    void getItemByIdFailed() throws Exception {

        mockMvc.perform(get(AppConstants.ITEM_URL_PREFIX + "/id/" + itemId ).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode.errorCodeName",
                        is(AppErrorResponse.ErrorCodeConstants.RECORD_NOT_FOUND.getErrorCodeName())));


    }
}
