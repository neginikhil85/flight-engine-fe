package com.learning.event;

import com.learning.model.dto.FlightInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightDeleteEvent {
    private FlightInfoDto flightInfo;
    private String operationalStatus;
    private LocalDateTime eventReceived;
}
