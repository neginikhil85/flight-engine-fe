package com.learning.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightCancelDto {
    private String operationalStatus;
    private String cancellationCode;
    private String serviceType;
    private String flightStatus;
}