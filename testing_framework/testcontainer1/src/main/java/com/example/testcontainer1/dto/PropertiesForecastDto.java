package com.example.testcontainer1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PropertiesForecastDto {

    private String units;
    private List<PeriodForecastDto> periods;

}
