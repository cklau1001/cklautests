package com.example.testcontainer1.unit;

import com.example.testcontainer1.configuration.AppConstants;
import com.example.testcontainer1.dto.ForecastDto;
import com.example.testcontainer1.services.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClientException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@Slf4j
@RestClientTest(WeatherService.class)
class WeatherServiceUnitTests {

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private WeatherService weatherService;

    @Value("${application.weather.url}")
    private String url;

    @Test
    void testGetForecastSuccess() {

        mockRestServiceServer.expect(requestTo(url + AppConstants.NY_WEATHER_URL))
                        .andRespond(withSuccess("""
                                {"properties": 
                                   {"units": "us"} 
                                }
                                """, MediaType.APPLICATION_JSON));

        ForecastDto forecastDto = weatherService.getForecast();
        assertThat(forecastDto.getProperties().getUnits()).isEqualTo("us");
        mockRestServiceServer.verify();
    }

    @Test
    void testGetForecastFailed() {
        mockRestServiceServer.expect(requestTo(url + AppConstants.NY_WEATHER_URL))
                .andRespond(withStatus(HttpStatus.NOT_FOUND).body("""
                        {
                           "message": "Invalid url"
                        }
                        """).contentType(MediaType.APPLICATION_JSON));
        assertThatThrownBy(() -> weatherService.getForecast())
                .isInstanceOf(RestClientException.class)
                .hasMessageContaining("404 NOT_FOUND")
                .hasMessageContaining("Invalid url");

        mockRestServiceServer.verify();
    }

}
