package com.learning.enums;

import com.learning.vaadin.ui.component.grid.filter.GridFilters;
import com.learning.vaadin.ui.component.grid.filter.impl.*;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Getter
public enum GridFilterBean {
    FLIGHT_LEG(FlightLegGridFilters::new),
    ACTUAL_TIMES(ActualTimesGridFilters::new),
    DELAY(DelayGridFilters::new),
    DIVERSION(DiversionGridFilters::new),
    DOOR_CLOSE(DoorCloseGridFilters::new),
    EQUIPMENT(EquipmentGridFilters::new),
    ESTIMATED_TIMES(EstimatedTimesGridFilters::new),
    FLIGHT_CANCEL(FlightCancelGridFilters::new),
    FLIGHT_DELETE(FlightDeleteGridFilters::new),
    FLIGHT_RETURN(FlightReturnGridFilters::new),
    GATE_CHANGE(GateChangeGridFilters::new),
    OPERATION_STATUS(OperationStatusGridFilters::new),
    TERMINAL(TerminalGridFilters::new),
    IN_BLOCK_TIME(InBlockTimeGridFilters::new),
    OFF_BLOCK_TIME(OffBlockTimeGridFilters::new),
    TAKE_OFF_TIME(TakeOffTimeGridFilters::new),
    LANDING_TIME(LandingTimeGridFilters::new);

    private final GridFilters<?> bean;
    private final SingletonHelper singletonHelper = new SingletonHelper();

    // Constructor takes a supplier to ensure lazy initialization
    <T> GridFilterBean(Supplier<GridFilters<T>> supplier) {
        this.bean = singletonHelper.initialize(supplier);
    }

    @SuppressWarnings("unchecked")
    public <T> GridFilters<T> getBean() {
        return (GridFilters<T>) bean;
    }

    private static class SingletonHelper {
        // Updated the map to use GridFilters<?> instead of Object
        private final Map<Supplier<? extends GridFilters<?>>, GridFilters<?>> instanceMap = new ConcurrentHashMap<>();

        private <T> GridFilters<T> initialize(Supplier<GridFilters<T>> supplier) {
            // Type-safe handling of instances in the map
            return (GridFilters<T>) instanceMap.computeIfAbsent(supplier, Supplier::get);
        }
    }
}
