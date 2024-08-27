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
public class OffBlockTimeEvent implements EventData {
    private FlightInfoDto flightInfo;
    private String currentOffBlock;
    private String previousOffBlock;
    private TimeType timeType;
    private LocalDateTime eventReceived;
}
