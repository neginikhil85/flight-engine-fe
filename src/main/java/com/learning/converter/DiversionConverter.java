package com.learning.converter;

import com.learning.event.DiversionEvent;
import com.learning.model.dto.FlightInfoDto;
import com.learning.model.grid.Diversion;
import com.learning.model.grid.FlightLegState;
import com.learning.util.CommonUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DiversionConverter implements GridConverter<DiversionEvent, Diversion> {
    @Override
    public Diversion convert(DiversionEvent event) {
        Diversion.DiversionBuilder<?, ?> builder = Diversion.builder();
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
        CommonUtils.setIfNotNull(event.getFlightStatus(), builder::flightStatus);
        CommonUtils.setIfNotNull(event.getDiversionCode(), builder::diversionCode);
        CommonUtils.setIfNotNull(event.getRegistration(), builder::registration);
        CommonUtils.setIfNotNull(event.getEffectiveArrivalStation(), builder::effectiveArrivalStation);
        CommonUtils.setIfNotNull(event.getEstimatedInBlock(), builder::estimatedInBlock);

        if (Objects.nonNull(event.getContinuationLeg())) {
            CommonUtils.setIfNotNull(getContinuationDataDto(event.getContinuationLeg()), builder::continuationLeg);
        }
        CommonUtils.setIfNotNull(event.getEventReceived(), builder::eventReceivedOn);
        return builder.build();
    }

    private FlightLegState.FlightContinuationDataDto getContinuationDataDto(
            com.learning.model.response.FlightLegState.OpsProperties.FlightContinuationData continuationLeg) {
        return FlightLegState.FlightContinuationDataDto.builder()
                .carrier(continuationLeg.getCarrier())
                .dateOfOrigin(continuationLeg.getDateOfOrigin())
                .flightNumber(continuationLeg.getFlightNumber())
                .suffix(continuationLeg.getSuffix())
                .startStation(continuationLeg.getStartStation())
                .endStation(continuationLeg.getEndStation())
                .scheduledStartTime(continuationLeg.getScheduledStartTime())
                .scheduledEndTime(continuationLeg.getScheduledEndTime())
                .startTimeOffset(continuationLeg.getStartTimeOffset())
                .endTimeOffset(continuationLeg.getEndTimeOffset())
                .seqNumber(continuationLeg.getSeqNumber())
                .build();
    }

    @Override
    public List<Diversion> convert(List<DiversionEvent> eventList) {
        List<Diversion> diversions = new ArrayList<>();
        eventList.forEach(event -> diversions.add(convert(event)));
        return diversions;
    }
}
