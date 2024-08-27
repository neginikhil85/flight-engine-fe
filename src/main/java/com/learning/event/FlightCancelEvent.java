package com.learning.event;

import com.learning.model.dto.FlightCancelDto;
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
public class FlightCancelEvent implements EventData {
    private FlightInfoDto flightInfo;
    private FlightCancelDto current;
    private FlightCancelDto previous;
    private LocalDateTime eventReceived;
}
