package com.learning.model.grid;

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
public class FlightLegState extends FlightInfoDto {
    private String suffix;
    private String seqNumber;
    private LocalDateTime scheduledEndTime;
    private String startTerminal;
    private String endTerminal;
    private String startGate;
    private String endGate;
    private String startStand;
    private String endStand;
    private String operationalStatus;
    private String cancellationCode;
    private String flightStatus;
    private String startStationICAO;
    private String endStationICAO;
    private String startCountry;
    private String endCountry;
    private String startTimeOffset;
    private String endTimeOffset;
    private String blockTimeActual;
    private String blockTimeSch;
    private String flightHoursActual;
    private String turnTimeFlightBeforeActual;
    private String turnTimeFlightBeforeSch;
    private String turnTimeFlightAfter;
    private String taxiInTime;
    private String taxiOutTime;
    private ReturnEventData returnEvents;
    private EquipmentData equipment;
    private OpsProperties operation;
    private DelayData delays;
    private HandlingData handlingData;
    private CodeShareData codeShares;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HandlingData {
        private String serviceType;
        private String aircraftOwner;
        private String cockpitEmployer;
        private String cabinEmployer;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FlightContinuationDataDto extends FlightInfoDto {
        private String suffix;
        private String scheduledEndTime;
        private String startTimeOffset;
        private String endTimeOffset;
        private Integer seqNumber;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReturnEventData {
        private List<ReturnAtom> returnEvent;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ReturnAtom {
            private Integer returnNumber;
            private EstimatedTimes estimatedTimes;
            private ActualTimes actualTimes;
            private String aircraft;
            private String scheduleStatus;
            private Integer inBlockFuel;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EquipmentData {
        private String plannedAircraftType;
        private AircraftInfo aircraft;
        private String aircraftConfiguration;
        private String passengerReservations;
        private String onwardFlight;
        private String tailLock;
        private String tailLockRemark;
        private String assignedAircraftTypeIATA;
        private String assignedAircraftTypeICAO;
        private String assignedAircraftTypeIndigo;
        private String plannedAircraftTypeICAO;
        private String plannedAircraftTypeIndigo;
        private String assignedAircraftConfiguration;
        private String aircraftRegistration;
        private String aircraftAnnotations;
        private String actualOnwardFlight;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class AircraftInfo {
            private String registration;
            private String type;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class OnwardFlightData {
            private String aircraftRotationLayover;
            private String carrier;
            private Integer flightNumber;
            private String suffix;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class AircraftAnnotationData {
            private List<AircraftAnnotation> aircraftAnnotations;

            @Data
            @Builder
            @AllArgsConstructor
            @NoArgsConstructor
            public static class AircraftAnnotation {
                private String code;
                private String text;
                private String createdBy;
                private String modifiedBy;
                private String modificationTime;
                private String validFrom;
                private String validTo;
            }
        }

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OpsProperties {
        private String desk;
        private String dispatcher;
        private String callSign;
        private String effectiveArrivalStation;
        private EstimatedTimes estimatedTimes;
        private ActualTimes actualTimes;
        private EstimatedDepSlot estimatedDepSlot;
        private InternalTime internalTimes;
        private ActualFuel fuel;
        private Boolean autoland;
        private String diversionCode;
        private LocalDateTime nextInformationTime;
        private FlightContinuationDataDto continuationLeg;
        private FlightPlan flightPlan;
        private OpsIncidentLock incidentLock;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class EstimatedDepSlot {
            private String depSlot;
            private Boolean isSlotCancelled;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class InternalTime {
            private String offBlock;
            private String inBlock;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ActualFuel {
            private Integer offBlock;
            private Integer takeOff;
            private Integer landing;
            private Integer inBlock;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class FlightPlan {
            private String estimatedElapsedTime;
            private Integer acTakeOffWeight;
            private Integer offBlockFuel;
            private Integer takeOffFuel;
            private Integer landingFuel;
            private Integer inBlockFuel;
            private Integer holdFuel;
            private String holdTime;
            private Integer routeDistance;
            private AlternativeAirportData alternatives;

            @Data
            @Builder
            @AllArgsConstructor
            @NoArgsConstructor
            public static class AlternativeAirportData {
                private List<String> departure;
                private List<String> intermediate;
                private List<String> arrival;
            }
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class OpsIncidentLock {
            private String createdBy;
            private String creationTime;
            private String remark;
        }

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DelayData {
        private String delayLimit;
        private String remarks;
        private String total;
        private List<DelayItem> delays;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class DelayItem {
            private String time;
            private String reason;
            private String delayNumber;
            private boolean isRootCause;
            private String remark;
        }

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CodeShareData {
        private List<CodeShare> codeShares;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class CodeShare {
            private String carrier;
            private Integer flightNumber;
            private String suffix;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EstimatedTimes {
        private String offBlock;
        private String inBlock;
        private String takeOffTime;
        private String landingTime;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ActualTimes {
        private String offBlock;
        private String inBlock;
        private String takeOffTime;
        private String landingTime;
        private String doorClose;
    }
}
