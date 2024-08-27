package com.learning.vaadin.ui.component.grid.provider.impl.diversion;


import com.learning.model.grid.Diversion;
import com.learning.util.CommonUtils;
import com.learning.vaadin.ui.component.grid.provider.ColumnProvider;
import com.vaadin.flow.function.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class DiversionColumnProvider implements ColumnProvider<Diversion> {
    @Override
    public Class<Diversion> beanType() {
        return Diversion.class;
    }

    @Override
    public Map<String, ValueProvider<Diversion, ?>> getHeaderAndValueProviders() {
        return new LinkedHashMap<>() {{
            put("Flight No.", Diversion::getFlightNumber);
            put("Date Of Origin", Diversion::getDateOfOrigin);
            put("Start Station", Diversion::getStartStation);
            put("End Station", Diversion::getEndStation);
            put("Scheduled Start Time", Diversion::getScheduledStartTime);
            put("Flight Status", Diversion::getFlightStatus);
            put("Effective Arrival Station", Diversion::getEffectiveArrivalStation);
            put("Diversion Reason", Diversion::getDiversionCode);
            put("Estimated In Block", Diversion::getEstimatedInBlock);
            put("Registration", Diversion::getRegistration);
            put("Continuation Flight No.", diversion -> diversion.getContinuationLeg().getFlightNumber());
            put("Continuation Date Of Origin", diversion -> diversion.getContinuationLeg().getDateOfOrigin());
            put("Continuation Start Station", diversion -> diversion.getContinuationLeg().getStartStation());
            put("Continuation end Station", diversion -> diversion.getContinuationLeg().getEndStation());
            put("Continuation Scheduled Start Time", diversion -> diversion.getContinuationLeg().getScheduledStartTime());
            put("Continuation suffix", diversion -> diversion.getContinuationLeg().getSuffix());
            put("Continuation scheduled End Time", diversion -> diversion.getContinuationLeg().getScheduledEndTime());
            put("Continuation Start Time Offset", diversion -> diversion.getContinuationLeg().getStartTimeOffset());
            put("Continuation End Time Offset", diversion -> diversion.getContinuationLeg().getEndTimeOffset());
            put("Continuation Seq No.", diversion -> diversion.getContinuationLeg().getSeqNumber());
            put("Event Received On", delayData -> CommonUtils.getFormattedDate(delayData.getEventReceivedOn()));
        }};
    }
}
