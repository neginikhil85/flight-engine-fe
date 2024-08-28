package com.learning.event;

import com.learning.enums.TimeType;
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
public class DelayEvent {
    private FlightInfoDto flightInfo;
    private String currentOffBlock;
    private String previousOffBlock;
    private FlightLegState.DelayData delays;
    private TimeType timeType;
    private LocalDateTime eventReceived;
}
