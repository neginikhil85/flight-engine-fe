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
public class InBlockTimeEvent {
    private FlightInfoDto flightInfo;
    private String currentInBlock;
    private String previousInBlock;
    private TimeType timeType;
    private LocalDateTime eventReceived;
}
