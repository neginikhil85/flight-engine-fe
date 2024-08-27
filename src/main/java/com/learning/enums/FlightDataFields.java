package com.learning.enums;

import lombok.Getter;

@Getter
public enum FlightDataFields {
    FLIGHT_NUMBER("FLIGHT NUMBER"),
    CARRIER("CARRIER"),
    DATE_OF_ORIGIN("DATE OF ORIGIN"),
    START_STATION("START STATION"),
    END_STATION("END STATION"),
    OPERATIONAL_STATUS("STATUS"),
    CANCELLATION_CODE("CANCELLATION CODE"),
    SCHEDULED_START_TIME("START TIME"),
    START_TERMINAL("START TERMINAL"),
    END_TERMINAL("END TERMINAL"),
    START_GATE("START GATE"),
    END_GATE("END GATE"),
    START_STAND("START STAND"),
    START_TIME_OFFSET("START TIME OFFSET"),
    END_TIME_OFFSET("END TIME OFFSET");

    private final String label;

    FlightDataFields(String label) {
        this.label = label;
    }
}
