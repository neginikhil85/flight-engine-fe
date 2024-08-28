package com.learning.converter;

import com.learning.event.GateChangeEvent;
import com.learning.model.dto.FlightInfoDto;
import com.learning.model.grid.GateChange;
import com.learning.util.CommonUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class GateChangeConverter implements GridConverter<GateChangeEvent, GateChange> {

    @Override
    public GateChange convert(GateChangeEvent event) {
        GateChange.GateChangeBuilder<?, ?> builder = GateChange.builder();
        if (Objects.isNull(event)) return builder.build();

        if (Objects.nonNull(event.getFlightInfo())) {
            FlightInfoDto flightInfo = event.getFlightInfo();
            CommonUtils.setIfNotNull(flightInfo.getFlightNumber(), builder::flightNumber);
            CommonUtils.setIfNotNull(flightInfo.getCarrier(), builder::carrier);
            CommonUtils.setIfNotNull(flightInfo.getDateOfOrigin(), builder::dateOfOrigin);
            CommonUtils.setIfNotNull(flightInfo.getStartStation(), builder::startStation);
            CommonUtils.setIfNotNull(flightInfo.getEndStation(), builder::endStation);
            CommonUtils.setIfNotNull(flightInfo.getScheduledStartTime(), builder::scheduledStartTime);
        }

        if (Objects.nonNull(event.getCurrent())) {
            CommonUtils.setIfNotNull(event.getCurrent(), builder::current);
        }

        CommonUtils.setIfNotNull(event.getEventReceived(), builder::eventReceivedOn);
        return builder.build();
    }

    @Override
    public List<GateChange> convert(List<GateChangeEvent> eventList) {
        List<GateChange> gateChanges = new ArrayList<>();
        eventList.forEach(event -> gateChanges.add(convert(event)));
        return gateChanges;
    }
}
