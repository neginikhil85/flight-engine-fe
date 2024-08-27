package com.learning.event;

import com.learning.model.dto.FlightInfoDto;
import com.learning.model.dto.GateTerminalDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GateChangeEvent implements EventData {
    private FlightInfoDto flightInfo;
    private GateTerminalDto current;
    private GateTerminalDto previous;
    private LocalDateTime eventReceived;
}
