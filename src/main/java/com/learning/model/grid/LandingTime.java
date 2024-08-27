package com.learning.model.grid;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.learning.enums.TimeType;
import com.learning.model.dto.FlightInfoDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class LandingTime extends FlightInfoDto {
    private String currentLandingTime;
    private String previousLandingTime;
    private TimeType timeType;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a")
    private LocalDateTime eventReceivedOn;
}
