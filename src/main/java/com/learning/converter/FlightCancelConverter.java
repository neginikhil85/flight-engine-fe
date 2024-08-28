package com.learning.converter;

import com.learning.event.FlightCancelEvent;
import com.learning.model.dto.FlightInfoDto;
import com.learning.model.grid.FlightCancel;
import com.learning.util.CommonUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class FlightCancelConverter implements GridConverter<FlightCancelEvent, FlightCancel> {

    @Override
    public FlightCancel convert(FlightCancelEvent event) {
        FlightCancel.FlightCancelBuilder<?, ?> builder = FlightCancel.builder();
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
    public List<FlightCancel> convert(List<FlightCancelEvent> eventList) {
        List<FlightCancel> flightCancels = new ArrayList<>();
        eventList.forEach(event -> flightCancels.add(convert(event)));
        return flightCancels;
    }
}
