package com.learning.event;

import com.learning.model.dto.EquipmentDto;
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
public class EquipmentEvent {
    private FlightInfoDto flightInfo;
    private EquipmentDto current;
    private EquipmentDto previous;
    private LocalDateTime eventReceived;
}
