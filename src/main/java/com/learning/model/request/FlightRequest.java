package com.learning.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightRequest {
    private String carrier;
    private Integer flightNumber;
    private Integer seqNumber;
    private String startStation;
    private String endStation;
    private String startDateTime;
    private String endDateTime;
    private String startTerminal;
    private String operationalStatus;
    private int page = 0;
    private int size = 10;
}