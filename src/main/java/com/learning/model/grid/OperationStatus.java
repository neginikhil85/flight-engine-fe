package com.learning.model.grid;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class OperationStatus extends FlightInfoDto {
    private String currentOperationalStatus;
    private String previousOperationalStatus;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a")
    private LocalDateTime eventReceivedOn;
}
