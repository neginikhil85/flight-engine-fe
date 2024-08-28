package com.learning.event;

import com.learning.model.dto.FlightInfoDto;
import com.learning.model.response.FlightLegState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiversionEvent {
    private FlightInfoDto flightInfo;
    private String flightStatus;
    private FlightLegState.OpsProperties.FlightContinuationData continuationLeg;
    private String effectiveArrivalStation;
    private String diversionCode;
    private String estimatedInBlock;
    private String registration;
    private LocalDateTime eventReceived;
}
