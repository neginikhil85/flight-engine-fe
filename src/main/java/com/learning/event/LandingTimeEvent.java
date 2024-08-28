package com.learning.event;

import com.learning.enums.TimeType;
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
public class LandingTimeEvent {
    private FlightInfoDto flightInfo;
    private String currentLandingTime;
    private String previousLandingTime;
    private TimeType timeType;
    private LocalDateTime eventReceived;
}
