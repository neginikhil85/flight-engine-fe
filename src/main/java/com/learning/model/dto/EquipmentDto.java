package com.learning.model.dto;

import com.learning.model.response.FlightLegState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDto {
    private String plannedAircraftType;
    private FlightLegState.EquipmentData.FlightLegAircraftInfo aircraft;
    private String aircraftConfiguration;
    private String assignedAircraftConfiguration;
}