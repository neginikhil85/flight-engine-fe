package com.learning.converter;

import com.learning.event.OperationStatusEvent;
import com.learning.model.dto.FlightInfoDto;
import com.learning.model.grid.OperationStatus;
import com.learning.util.CommonUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class OperationStatusConverter implements GridConverter<OperationStatusEvent, OperationStatus> {

    @Override
    public OperationStatus convert(OperationStatusEvent event) {
        OperationStatus.OperationStatusBuilder<?, ?> builder = OperationStatus.builder();
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
    public List<OperationStatus> convert(List<OperationStatusEvent> eventList) {
        List<OperationStatus> operationStatuses = new ArrayList<>();
        eventList.forEach(event -> operationStatuses.add(convert(event)));
        return operationStatuses;
    }
}
