package com.learning.converter;

import com.learning.event.DelayEvent;
import com.learning.model.dto.FlightInfoDto;
import com.learning.model.grid.Delay;
import com.learning.model.response.FlightLegState;
import com.learning.util.CommonUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DelayConverter implements GridConverter<DelayEvent, Delay> {

    public Delay convert(DelayEvent event) {
        Delay.DelayBuilder<?, ?> builder = Delay.builder();
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

        if (Objects.nonNull(event.getDelays())) {
            FlightLegState.DelayData delayData = event.getDelays();
            CommonUtils.setIfNotNull(delayData.getDelayLimit(), builder::delayLimit);
            CommonUtils.setIfNotNull(delayData.getRemark(), builder::remarks);
            CommonUtils.setIfNotNull(delayData.getTotal(), builder::total);
            CommonUtils.setIfNotNull(delayData.getTotal(), builder::total);

            if (!CollectionUtils.isEmpty(delayData.getDelay())) {
                CommonUtils.setIfNotNull(delayData.getDelay().stream().map(this::buildDelayItem).toList(), builder::delays);
            }
        }
        CommonUtils.setIfNotNull(event.getEventReceived(), builder::eventReceivedOn);
        return builder.build();
    }

    @Override
    public List<Delay> convert(List<DelayEvent> eventList) {
        List<Delay> delays = new ArrayList<>();
        eventList.forEach(event -> delays.add(convert(event)));
        return delays;
    }

    private Delay.DelayItem buildDelayItem(FlightLegState.DelayData.DelayItem delayItem) {
        return Delay.DelayItem.builder()
                .delayNumber(delayItem.getDelayNumber())
                .time(delayItem.getTime())
                .reason(delayItem.getReason())
                .isRootCause(delayItem.getIsRootCause())
                .remark(delayItem.getRemark())
                .build();
    }
}
