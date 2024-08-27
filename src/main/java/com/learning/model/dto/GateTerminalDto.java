package com.learning.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GateTerminalDto {
    private String startGate;
    private String endGate;
    private String startTerminal;
    private String endTerminal;
    private String startStand;
    private String endStand;
}