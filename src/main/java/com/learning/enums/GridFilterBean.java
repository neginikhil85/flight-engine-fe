package com.learning.enums;

import com.learning.vaadin.ui.component.grid.filter.GridFilters;
import com.learning.vaadin.ui.component.grid.filter.impl.*;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Getter
public enum GridFilterBean {
    FLIGHT_DATA(FlightDataGridFilters::new),
    ACTUAL_TIMES(ActualTimesDataGridFilters::new),
    DELAY_DATA(DelayDataGridFilters::new),
    DIVERSION_DATA(DiversionDataGridFilters::new),
    DOOR_CLOSE(DoorCloseDataGridFilters::new),
    EQUIPMENT(EquipmentDataGridFilters::new),
    ESTIMATED_TIMES(EstimatedTimesDataGridFilters::new),
    FLIGHT_CANCEL(FlightCancelDataGridFilters::new),
    FLIGHT_DELETE(FlightDeleteDataGridFilters::new),
    FLIGHT_RETURN(FlightReturnDataGridFilters::new),
    GATE_CHANGE(GateChangeDataGridFilters::new),
    OPERATION_STATUS(OperationStatusDataGridFilters::new),
    TERMINAL(TerminalDataGridFilters::new);

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
