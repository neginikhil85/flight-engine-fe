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
public class FlightReturnEvent implements EventData {
    private FlightInfoDto flightInfo;
    private FlightLegState.ReturnEventData.ReturnAtom returnAtom;
    private LocalDateTime eventReceived;
}
