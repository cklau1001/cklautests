package com.example.testcontainer1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ForecastDto {

    private PropertiesForecastDto properties;

}
