package com.learning.vaadin.ui.component.grid.provider.impl.delay;

import com.learning.model.grid.Delay;
import com.learning.util.CommonUtils;
import com.learning.vaadin.ui.component.dialog.DelayDialog;
import com.learning.vaadin.ui.component.grid.provider.ColumnProvider;
import com.learning.vaadin.ui.component.grid.provider.ColumnProviderFactory;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class DelayDataColumnProvider implements ColumnProvider<Delay> {

    @Override
    public Class<Delay> beanType() {
        return Delay.class;
    }

    @Override
    public Map<String, ValueProvider<Delay, ?>> getHeaderAndValueProviders() {
        return new LinkedHashMap<>() {{
            put("Flight No.", Delay::getFlightNumber);
            put("Date Of Origin", Delay::getDateOfOrigin);
            put("Start Station", Delay::getStartStation);
            put("End Station", Delay::getEndStation);
            put("Scheduled Start Time", Delay::getScheduledStartTime);
            put("Delay limit", Delay::getDelayLimit);
            put("Delay Remarks", Delay::getRemarks);
            put("Total Delays", Delay::getTotal);
            put("Event Received On", delay -> CommonUtils.getFormattedDate(delay.getEventReceivedOn()));
        }};
    }

    @Override
    public List<CustomColumn<Delay>> getCustomColumns(ColumnProviderFactory factory) {
        return List.of(
                new CustomColumn<>("Delays", new ComponentRenderer<>(item -> {
                    Button viewBtn = new Button("view");
                    viewBtn.addClassNames("compact-button");
                    viewBtn.addClickListener(click -> {
                        DelayDialog dialog = new DelayDialog(item.getDelays(), factory);
                        dialog.addClassName("custom-dialog");
                        dialog.open();
                    });
                    return viewBtn;
                }))
        );
    }
}
