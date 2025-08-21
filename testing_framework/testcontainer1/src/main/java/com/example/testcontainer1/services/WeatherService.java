package com.example.testcontainer1.services;

import com.example.testcontainer1.configuration.AppConstants;
import com.example.testcontainer1.dto.ForecastDto;

import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;


@Slf4j
@Service
public class WeatherService {

    private final RestClient restClient;

    private final String url;

    /**
     * Create the WeatherService service by constructor injection
     * @param restClientBuilder - the builder to create RestClient in Spring
     * @param url - the weather api url
     */
    public WeatherService(RestClient.Builder restClientBuilder, @Value("${application.weather.url}") String url) {
        this.url = url;
        this.restClient = restClientBuilder.baseUrl(url).build();
    }

    public ForecastDto getForecast() throws RestClientException {

        log.info("[getForecast]: entered: url={}", url);

        try {
            ForecastDto response = restClient.get()
                    .uri(AppConstants.NY_WEATHER_URL)
                    .headers(h -> {
                        h.set("User-Agent", "testing123");
                        h.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                        h.setContentType(MediaType.APPLICATION_JSON);
                    })
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, ((req, resp) -> {
                        throw new RestClientException(String.format("[getForecast]: Hit issue from external service, statusCode=[%s], error=[%s]",
                                resp.getStatusCode(),  new String(resp.getBody().readAllBytes())));
                    }))
                    .body(ForecastDto.class);
            log.info("[getForecast]: response={}", response);
            return response;

        } catch (Exception e) {
            throw new RestClientException("[getForecast]: issue in getting forecast, error=" +
                    e.getMessage().replaceAll("\\n", " "), e);
        }
    }


}
