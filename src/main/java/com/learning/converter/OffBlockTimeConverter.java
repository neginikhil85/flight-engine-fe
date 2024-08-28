package com.learning.converter;

import com.learning.event.OffBlockTimeEvent;
import com.learning.model.dto.FlightInfoDto;
import com.learning.model.grid.OffBlockTime;
import com.learning.util.CommonUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class OffBlockTimeConverter implements GridConverter<OffBlockTimeEvent, OffBlockTime> {

    @Override
    public OffBlockTime convert(OffBlockTimeEvent event) {
        OffBlockTime.OffBlockTimeBuilder<?, ?> builder = OffBlockTime.builder();
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
        CommonUtils.setIfNotNull(event.getCurrentOffBlock(), builder::currentOffBlock);
        CommonUtils.setIfNotNull(event.getTimeType(), builder::timeType);
        CommonUtils.setIfNotNull(event.getEventReceived(), builder::eventReceivedOn);
        return builder.build();
    }

    @Override
    public List<OffBlockTime> convert(List<OffBlockTimeEvent> eventList) {
        List<OffBlockTime> offBlockTimes = new ArrayList<>();
        eventList.forEach(event -> offBlockTimes.add(convert(event)));
        return offBlockTimes;
    }
}
