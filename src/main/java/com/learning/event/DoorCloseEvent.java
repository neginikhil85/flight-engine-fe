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
public class DoorCloseEvent {
    private FlightInfoDto flightInfo;
    private FlightLegState.ActualTimes currentActualTimes;
    private FlightLegState.ActualTimes previousActualTimes;
    private LocalDateTime eventReceived;
}
