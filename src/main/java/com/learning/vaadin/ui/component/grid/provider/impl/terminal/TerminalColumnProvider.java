package com.learning.vaadin.ui.component.grid.provider.impl.terminal;

import com.learning.model.grid.Terminal;
import com.learning.util.CommonUtils;
import com.learning.vaadin.ui.component.grid.provider.ColumnProvider;
import com.vaadin.flow.function.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class TerminalColumnProvider implements ColumnProvider<Terminal> {

    @Override
    public Class<Terminal> beanType() {
        return Terminal.class;
    }

    @Override
    public Map<String, ValueProvider<Terminal, ?>> getHeaderAndValueProviders() {
        return  new LinkedHashMap<>() {{
            put("Flight No.", Terminal::getFlightNumber);
            put("Date Of Origin", Terminal::getDateOfOrigin);
            put("Start Station", Terminal::getStartStation);
            put("End Station", Terminal::getEndStation);
            put("Scheduled Start Time", Terminal::getScheduledStartTime);
            put("Start Terminal", safeValueProvider(terminal -> terminal.getCurrent().getStartTerminal()));
            put("End Terminal", safeValueProvider(terminal -> terminal.getCurrent().getEndTerminal()));
            put("Start Gate", safeValueProvider(terminal -> terminal.getCurrent().getStartGate()));
            put("End Gate", safeValueProvider(terminal -> terminal.getCurrent().getEndGate()));
            put("Start Stand", safeValueProvider(terminal -> terminal.getCurrent().getStartStand()));
            put("End Stand", safeValueProvider(terminal -> terminal.getCurrent().getEndStand()));
            put("Event Received On", safeValueProvider(terminal -> CommonUtils.getFormattedDate(terminal.getEventReceivedOn())));
        }};
    }
}
