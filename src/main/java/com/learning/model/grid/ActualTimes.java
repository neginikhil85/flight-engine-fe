package com.learning.model.grid;

import com.learning.model.dto.FlightInfoDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class ActualTimes extends FlightInfoDto {
    private InBlockTime inBlockTime;
    private OffBlockTime offBlockTime;
    private TakeOffTime takeOffTime;
    private LandingTime landingTime;

}
