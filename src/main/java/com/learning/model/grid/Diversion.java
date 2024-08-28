package com.learning.model.grid;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.learning.model.dto.FlightInfoDto;
import com.learning.model.response.FlightLegState;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class Diversion extends FlightInfoDto {
    private String flightStatus;
    private String effectiveArrivalStation;
    private String diversionCode;
    private String estimatedInBlock;
    private String registration;
    private FlightLegState.OpsProperties.FlightContinuationData continuationLeg;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a")
    private LocalDateTime eventReceivedOn;
}
