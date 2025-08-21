package com.example.testcontainer1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PeriodForecastDto {

    private int number;
    private String name;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private boolean isDaytime;
    private int temperature;
    private String temperatureUnit;
}
