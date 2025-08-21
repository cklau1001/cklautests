package com.example.testcontainer1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class HeartBeatDto {

    private String message;
    private String ip;
    private Date pingTime;

}
