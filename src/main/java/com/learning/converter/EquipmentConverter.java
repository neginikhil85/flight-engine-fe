package com.learning.converter;

import com.learning.event.EquipmentEvent;
import com.learning.model.dto.FlightInfoDto;
import com.learning.model.grid.Equipment;
import com.learning.util.CommonUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class EquipmentConverter implements GridConverter<EquipmentEvent, Equipment> {

    @Override
    public Equipment convert(EquipmentEvent event) {
        Equipment.EquipmentBuilder<?, ?> builder = Equipment.builder();
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
    public List<Equipment> convert(List<EquipmentEvent> eventList) {
        List<Equipment> equipmentList = new ArrayList<>();
        eventList.forEach(event -> equipmentList.add(convert(event)));
        return equipmentList;
    }
}
