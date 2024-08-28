package com.learning.converter;

import com.learning.event.InBlockTimeEvent;
import com.learning.model.dto.FlightInfoDto;
import com.learning.model.grid.InBlockTime;
import com.learning.util.CommonUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class InBlockTimeConverter implements GridConverter<InBlockTimeEvent, InBlockTime> {

    @Override
    public InBlockTime convert(InBlockTimeEvent event) {
        InBlockTime.InBlockTimeBuilder<?, ?> builder = InBlockTime.builder();
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
        CommonUtils.setIfNotNull(event.getCurrentInBlock(), builder::currentInBlock);
        CommonUtils.setIfNotNull(event.getTimeType(), builder::timeType);
        CommonUtils.setIfNotNull(event.getEventReceived(), builder::eventReceivedOn);
        return builder.build();
    }

    @Override
    public List<InBlockTime> convert(List<InBlockTimeEvent> eventList) {
        List<InBlockTime> inBlockTimes = new ArrayList<>();
        eventList.forEach(event -> inBlockTimes.add(convert(event)));
        return inBlockTimes;
    }
}
