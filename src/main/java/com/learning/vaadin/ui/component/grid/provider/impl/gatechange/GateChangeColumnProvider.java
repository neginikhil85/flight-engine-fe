package com.learning.vaadin.ui.component.grid.provider.impl.gatechange;


import com.learning.model.grid.GateChange;
import com.learning.util.CommonUtils;
import com.learning.vaadin.ui.component.grid.provider.ColumnProvider;
import com.vaadin.flow.function.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class GateChangeColumnProvider implements ColumnProvider<GateChange> {

    @Override
    public Class<GateChange> beanType() {
        return GateChange.class;
    }

    @Override
    public Map<String, ValueProvider<GateChange, ?>> getHeaderAndValueProviders() {
        return  new LinkedHashMap<>() {{
            put("Flight No.", GateChange::getFlightNumber);
            put("Date Of Origin", GateChange::getDateOfOrigin);
            put("Start Station", GateChange::getStartStation);
            put("End Station", GateChange::getEndStation);
            put("Scheduled Start Time", GateChange::getScheduledStartTime);
            put("Start Terminal", safeValueProvider(gateChange -> gateChange.getCurrent().getStartTerminal()));
            put("End Terminal", safeValueProvider(gateChange -> gateChange.getCurrent().getEndTerminal()));
            put("Start Gate", safeValueProvider(gateChange -> gateChange.getCurrent().getStartGate()));
            put("End Gate", safeValueProvider(gateChange -> gateChange.getCurrent().getStartGate()));
            put("Start Stand", safeValueProvider(gateChange -> gateChange.getCurrent().getStartStand()));
            put("End Stand", safeValueProvider(gateChange -> gateChange.getCurrent().getEndStand()));
            put("Event Received On", safeValueProvider(gateChange -> CommonUtils.getFormattedDate(gateChange.getEventReceivedOn())));
        }};
    }
}
