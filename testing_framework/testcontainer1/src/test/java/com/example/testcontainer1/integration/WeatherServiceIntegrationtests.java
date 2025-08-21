package com.example.testcontainer1.integration;

import com.example.testcontainer1.configuration.AppConstants;
import com.example.testcontainer1.dto.ForecastDto;
import com.example.testcontainer1.services.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.MediaType;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestClientException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

/*
  Set package-private for JUnit 5 test cases
 */

@Slf4j
@ExtendWith(MockServerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherServiceIntegrationtests {

    @Autowired
    WeatherService weatherService;

    private final MockServerClient mockServerClient;

    private static int port1;

    WeatherServiceIntegrationtests(MockServerClient mockServerClient) {
        this.mockServerClient = mockServerClient;
        port1 = mockServerClient.getPort();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("application.weather.url", () -> "http://localhost:" +  port1);
        log.info("[configureProperties]: application.weather.url configured. port={}",  port1);
    }

    @BeforeEach
    void startEachTest() {
        // reset before running each test case
        mockServerClient.reset();
        log.info("[startEachTest]: Reset mockServerClient");
    }

    @AfterEach
    void completeEachTest() {
        log.info("[completeEachTest]");
    }

    @Test
    void testGetForecastSuccess() {
        String method = "GET";
        String path = AppConstants.NY_WEATHER_URL;

        mockServerClient.when(
                request().withMethod(method).withPath(path)
        ).respond(
                response().withStatusCode(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                        .withBody(json("""
                               {"properties": 
                                   {"units": "us"} 
                                }
                        """))
        );
        ForecastDto forecastDto = weatherService.getForecast();
        assertThat(forecastDto.getProperties().getUnits()).isEqualTo("us");

        mockServerClient.verify(
                request().withMethod(method).withPath(path),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void testGetForecastFailed() {
        String method = "GET";
        String path = AppConstants.NY_WEATHER_URL;

        mockServerClient.when(
                request().withMethod(method).withPath(path)
        ).respond(
          response().withStatusCode(HttpStatus.NOT_FOUND.value())
                  .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                  .withBody(json(
                        """
                        {
                           "message": "Invalid url"
                        }
                        """))
        );

        assertThatThrownBy(() -> weatherService.getForecast())
                .isInstanceOf(RestClientException.class)
                .hasMessageContaining("404 NOT_FOUND")
                .hasMessageContaining("Invalid url");

        mockServerClient.verify(
                request().withMethod(method).withPath(path),
                VerificationTimes.exactly(1)
        );


    }

}
