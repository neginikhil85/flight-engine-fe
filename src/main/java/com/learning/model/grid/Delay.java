package com.learning.model.grid;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.learning.model.dto.FlightInfoDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Delay extends FlightInfoDto {
    private String delayLimit;
    private String remarks;
    private String total;
    private List<DelayItem> delays;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a")
    private LocalDateTime eventReceivedOn;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DelayItem {
        private String time;
        private String reason;
        private Integer delayNumber;
        private boolean isRootCause;
        private String remark;
    }

}
