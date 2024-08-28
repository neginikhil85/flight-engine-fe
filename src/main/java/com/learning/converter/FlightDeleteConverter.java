package com.learning.converter;

import com.learning.event.FlightDeleteEvent;
import com.learning.model.dto.FlightInfoDto;
import com.learning.model.grid.FlightDelete;
import com.learning.util.CommonUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class FlightDeleteConverter implements GridConverter<FlightDeleteEvent, FlightDelete> {

    @Override
    public FlightDelete convert(FlightDeleteEvent event) {
        FlightDelete.FlightDeleteBuilder<?, ?> builder = FlightDelete.builder();
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

        CommonUtils.setIfNotNull(event.getOperationalStatus(), builder::operationalStatus);

        CommonUtils.setIfNotNull(event.getEventReceived(), builder::eventReceivedOn);
        return builder.build();
    }

    @Override
    public List<FlightDelete> convert(List<FlightDeleteEvent> eventList) {
        List<FlightDelete> flightDeletes = new ArrayList<>();
        eventList.forEach(event -> flightDeletes.add(convert(event)));
        return flightDeletes;
    }
}
