package com.learning.vaadin.ui.component.grid.provider.impl.operationstatus;

import com.learning.model.grid.OperationStatus;
import com.learning.util.CommonUtils;
import com.learning.vaadin.ui.component.grid.provider.ColumnProvider;
import com.vaadin.flow.function.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class OperationStatusColumnProvider implements ColumnProvider<OperationStatus> {

    @Override
    public Class<OperationStatus> beanType() {
        return OperationStatus.class;
    }

    @Override
    public Map<String, ValueProvider<OperationStatus, ?>> getHeaderAndValueProviders() {
        return  new LinkedHashMap<>() {{
            put("Flight No.", OperationStatus::getFlightNumber);
            put("Date Of Origin", OperationStatus::getDateOfOrigin);
            put("Start Station", OperationStatus::getStartStation);
            put("End Station", OperationStatus::getEndStation);
            put("Scheduled Start Time", OperationStatus::getScheduledStartTime);
            put("Operational Status", OperationStatus::getCurrentOperationalStatus);
            put("Event Received On", safeValueProvider(operationStatus -> CommonUtils.getFormattedDate(operationStatus.getEventReceivedOn())));
        }};
    }
}
