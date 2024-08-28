package com.learning.converter;

import com.learning.event.TakeOffTimeEvent;
import com.learning.model.dto.FlightInfoDto;
import com.learning.model.grid.TakeOffTime;
import com.learning.util.CommonUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class TakeOffTimeConverter implements GridConverter<TakeOffTimeEvent, TakeOffTime> {

    @Override
    public TakeOffTime convert(TakeOffTimeEvent event) {
        TakeOffTime.TakeOffTimeBuilder<?, ?> builder = TakeOffTime.builder();
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
        CommonUtils.setIfNotNull(event.getCurrentTakeOffTime(), builder::currentTakeOffTime);
        CommonUtils.setIfNotNull(event.getTimeType(), builder::timeType);
        CommonUtils.setIfNotNull(event.getEventReceived(), builder::eventReceivedOn);
        return builder.build();
    }

    @Override
    public List<TakeOffTime> convert(List<TakeOffTimeEvent> eventList) {
        List<TakeOffTime> takeOffTimes = new ArrayList<>();
        eventList.forEach(event -> takeOffTimes.add(convert(event)));
        return takeOffTimes;
    }
}
