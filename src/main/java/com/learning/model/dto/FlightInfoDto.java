package com.learning.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FlightInfoDto {
    private String carrier;
    private String dateOfOrigin;
    private Integer flightNumber;
    private String startStation;
    private String endStation;
    private String scheduledStartTime;
}