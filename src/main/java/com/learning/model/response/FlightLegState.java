package com.learning.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightLegState implements Serializable {

    private String carrier;
    private String dateOfOrigin;
    private Integer flightNumber;
    private String suffix;
    private Integer seqNumber;
    private String startStation;
    private String endStation;
    private LocalDateTime scheduledStartTime;
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
    private HandlingData handling;
    private CodeShareData codeShares;

    @Data
    @NoArgsConstructor
    public static class ReturnEventData {

        private List<ReturnAtom> returnEvent;

        @Data
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
    @NoArgsConstructor
    public static class HandlingData {
        private String serviceType;
        private String aircraftOwner;
        private String cockpitEmployer;
        private String cabinEmployer;
    }

    @Data
    @NoArgsConstructor
    public static class CodeShareData {
        private List<CodeShare> codeShare;

        @Data
        @NoArgsConstructor
        public static class CodeShare {
            private String carrier;
            private Integer flightNumber;
            private String suffix;
        }
    }

    @Data
    @NoArgsConstructor
    public static class ActualTimes {
        private String offBlock;
        private String inBlock;
        private String takeoffTime;
        private String landingTime;
        private String doorClose;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DelayData {

        private String total;
        private String delayLimit;
        private String remark;
        private List<DelayItem> delay;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class DelayItem {
            private String time;
            private String reason;
            private Integer delayNumber;
            private Boolean isRootCause;
            private String remark;
        }
    }

    @Data
    @NoArgsConstructor
    public static class EquipmentData {
        private String plannedAircraftType;
        private FlightLegAircraftInfo aircraft;
        private String aircraftConfiguration;
        private String passengerReservations;
        private OnwardFlightData onwardFlight;
        private Boolean tailLock;
        private String tailLockRemark;
        private String assignedAircraftTypeIATA;
        private String assignedAircraftTypeICAO;
        private String assignedAircraftTypeIndigo;
        private String plannedaircraftTypeICAO;
        private String plannedaircraftTypeIndigo;
        private String assignedAircraftConfiguration;
        private String aircraftRegistration;
        private AircraftAnnotationData aircraftAnnotations;

        @Data
        @NoArgsConstructor
        public static class FlightLegAircraftInfo {
            private String registration;
            private String type;
        }

        @Data
        @NoArgsConstructor
        public static class OnwardFlightData {
            private String aircraftRotationLayover;
            private String carrier;
            private Integer flightNumber;
            private String suffix;
        }

        @Data
        @NoArgsConstructor
        public static class AircraftAnnotationData {
            private List<AircraftAnnotation> aircraftAnnotation;

            @Data
            @NoArgsConstructor
            public static class AircraftAnnotation {
                private String code;
                private String text;
                private String createdBy;
                private String creationTime;
                private String modifiedBy;
                private String modificationTime;
                private String validFrom;
                private String validTo;
            }
        }
    }

    @Data
    @NoArgsConstructor
    public static class EstimatedTimes {
        private String offBlock;
        private String inBlock;
        private String takeoffTime;
        private String landingTime;
    }

    @Data
    @NoArgsConstructor
    public static class OpsProperties {
        private String desk;
        private String dispatcher;
        private String callSign;
        private String effectiveArrivalStation;
        private String effectiveArrivalTerminal;
        private EstimatedTimes estimatedTimes;
        private ActualTimes actualTimes;
        private EstimatedDepSlot estimatedDepSlot;
        private InternalTimes internalTimes;
        private ActualFuel fuel;
        private Boolean autoland;
        private String diversionCode;
        private LocalDateTime nextInformationTime;
        private FlightContinuationData continuationLeg;
        private FlightPlan flightPlan;
        private OpsIncidentLock incidentLock;

        @Data
        @NoArgsConstructor
        public static class EstimatedDepSlot {
            private String depSlot;
            private Boolean isSlotCancelled;
        }

        @Data
        @NoArgsConstructor
        public static class InternalTimes {
            private String offBlock;
            private String inBlock;
        }

        @Data
        @NoArgsConstructor
        public static class ActualFuel {
            private Integer offBlock;
            private Integer takeoff;
            private Integer landing;
            private Integer inBlock;
        }

        @Data
        @NoArgsConstructor
        public static class FlightContinuationData {
            private String carrier;
            private String dateOfOrigin;
            private Integer flightNumber;
            private String suffix;
            private String startStation;
            private String endStation;
            private String scheduledStartTime;
            private String scheduledEndTime;
            private String startTimeOffset;
            private String endTimeOffset;
            private Integer seqNumber;
        }

        @Data
        @NoArgsConstructor
        public static class FlightPlan {
            private String estimatedElapsedTime;
            private Integer acTakeoffWeight;
            private Integer offBlockFuel;
            private Integer takeoffFuel;
            private Integer landingFuel;
            private Integer inBlockFuel;
            private Integer holdFuel;
            private String holdTime;
            private Integer routeDistance;
            private AlternateAirportData alternates;

            @Data
            @NoArgsConstructor
            public static class AlternateAirportData {
                private List<String> departure;
                private List<String> intermediate;
                private List<String> arrival;
            }
        }

        @Data
        @NoArgsConstructor
        public static class OpsIncidentLock {
            private String createdBy;
            private String creationTime;
            private String remark;
        }
    }
}