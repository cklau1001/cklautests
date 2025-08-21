package com.example.testcontainer1.integration;

import com.example.testcontainer1.dto.OrderItemDto;
import com.example.testcontainer1.dto.PurchaseOrderDto;
import com.example.testcontainer1.model.OrderItem;
import com.example.testcontainer1.model.PurchaseOrder;
import com.example.testcontainer1.repository.PurchaseOrderRepository;
import com.example.testcontainer1.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.function.Supplier;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
  This is a simple demo on how to perform JPA testing by using a test container. PostgreSQL is used.
  Pros:
    - More accurate reflection to the actual environment. For example, if the target DB is PostgresSQL,
    the testing results from using PostgresSQL container better reveal any potential underlying issues, boosting up
    confidence level that the code will work in real environment.
    - Data can be fed into the test container to perform testing on different edge cases
    - No need to perform different kinds of mocking

  Cons:
    - need to load the testcontainer image that implies the setup of a docker daemon. (or Docker desktop on windows)

  Reference
    https://testcontainers.com/guides/testing-spring-boot-rest-api-using-testcontainers/
 */

@Slf4j
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceIntegrationTests {

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

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Test
    void testInit_success() {


        PurchaseOrderDto po = orderService.init(orderId);

        log.info("[testInit_success]: orderId={}", po.getOrderId());
        assertThat(po.getOrderId()).isEqualTo(orderId);

        OrderItemDto orderItemDto = po.getOrderItemDtoList().getFirst();
        log.info("[testInit_success]: itemId={}", orderItemDto.getItemId());
        assertThat(orderItemDto.getItemId()).isEqualTo(itemId);

    }

    @Test
    void testGetOrderFailed() {

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> orderService.getOrderById("INVALID_ORDER_ID")
        );
        log.info("[testGetOrderFailed]: errorMessage={}", thrown.getMessage());
        assertThat(thrown.getMessage()).startsWith("No such order=");

    }

    @Test
    void testGetItemFailed() {

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> orderService.getItemById("INVALID_ITEM_ID")
        );
        log.info("[testGetItemFailed]: errorMessage={}", thrown.getMessage());
        assertThat(thrown.getMessage()).startsWith("No such item=");

    }

    @Test
    void testDeleteAllSuccess() {
        orderService.init("dummyId");
        orderService.deleteAll();

        assertThat(purchaseOrderRepository.count()).isEqualTo(0);
    }
}
