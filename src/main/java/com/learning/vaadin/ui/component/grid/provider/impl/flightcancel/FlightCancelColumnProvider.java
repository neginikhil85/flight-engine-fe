package com.learning.vaadin.ui.component.grid.provider.impl.flightcancel;


import com.learning.model.grid.FlightCancel;
import com.learning.util.CommonUtils;
import com.learning.vaadin.ui.component.grid.provider.ColumnProvider;
import com.vaadin.flow.function.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class FlightCancelColumnProvider implements ColumnProvider<FlightCancel> {
    @Override
    public Class<FlightCancel> beanType() {
        return FlightCancel.class;
    }

    @Override
    public Map<String, ValueProvider<FlightCancel, ?>> getHeaderAndValueProviders() {
        return  new LinkedHashMap<>() {{
            put("Flight No.", FlightCancel::getFlightNumber);
            put("Date Of Origin", FlightCancel::getDateOfOrigin);
            put("Start Station", FlightCancel::getStartStation);
            put("End Station", FlightCancel::getEndStation);
            put("Schedule Start Time", FlightCancel::getScheduledStartTime);
            put("Flight Status", safeValueProvider(flightCancel -> flightCancel.getCurrent().getFlightStatus()));
            put("Cancellation Code", safeValueProvider(flightCancel -> flightCancel.getCurrent().getCancellationCode()));
            put("Operational Status", safeValueProvider(flightCancel -> flightCancel.getCurrent().getOperationalStatus()));
            put("Service Type", safeValueProvider(flightCancel -> flightCancel.getCurrent().getServiceType()));
            put("Event Received On", delayData -> CommonUtils.getFormattedDate(delayData.getEventReceivedOn()));
        }};
    }
}
