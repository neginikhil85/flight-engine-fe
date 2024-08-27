package com.learning.converter;

import com.learning.event.LandingTimeEvent;
import com.learning.model.dto.FlightInfoDto;
import com.learning.model.grid.LandingTime;
import com.learning.util.CommonUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class LandingTimeConverter implements GridConverter<LandingTimeEvent, LandingTime> {

    @Override
    public LandingTime convert(LandingTimeEvent event) {
        LandingTime.LandingTimeBuilder<?, ?> builder = LandingTime.builder();
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
        CommonUtils.setIfNotNull(event.getEventReceived(), builder::eventReceivedOn);
        return builder.build();
    }

    @Override
    public List<LandingTime> convert(List<LandingTimeEvent> eventList) {
        List<LandingTime> landingTimes = new ArrayList<>();
        eventList.forEach(event -> landingTimes.add(convert(event)));
        return landingTimes;
    }
}
