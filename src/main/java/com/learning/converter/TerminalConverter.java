package com.learning.converter;

import com.learning.event.TerminalEvent;
import com.learning.model.dto.FlightInfoDto;
import com.learning.model.grid.Terminal;
import com.learning.util.CommonUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class TerminalConverter implements GridConverter<TerminalEvent, Terminal> {

    @Override
    public Terminal convert(TerminalEvent event) {
        Terminal.TerminalBuilder<?, ?> builder = Terminal.builder();
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
    public List<Terminal> convert(List<TerminalEvent> eventList) {
        List<Terminal> terminals = new ArrayList<>();
        eventList.forEach(event -> terminals.add(convert(event)));
        return terminals;
    }
}
