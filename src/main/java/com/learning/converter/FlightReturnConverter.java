package com.learning.converter;

import com.learning.event.FlightReturnEvent;
import com.learning.model.dto.FlightInfoDto;
import com.learning.model.grid.FlightReturn;
import com.learning.util.CommonUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class FlightReturnConverter implements GridConverter<FlightReturnEvent, FlightReturn> {

    @Override
    public FlightReturn convert(FlightReturnEvent event) {
        FlightReturn.FlightReturnBuilder<?, ?> builder = FlightReturn.builder();
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

        if (Objects.nonNull(event.getReturnAtom())) {
            CommonUtils.setIfNotNull(event.getReturnAtom(), builder::returnAtom);
        }
        CommonUtils.setIfNotNull(event.getEventReceived(), builder::eventReceivedOn);
        return builder.build();
    }

    @Override
    public List<FlightReturn> convert(List<FlightReturnEvent> eventList) {
        List<FlightReturn> flightReturns = new ArrayList<>();
        eventList.forEach(event -> flightReturns.add(convert(event)));
        return flightReturns;
    }
}
