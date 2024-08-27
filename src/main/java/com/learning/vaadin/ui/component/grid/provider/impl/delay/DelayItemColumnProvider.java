package com.learning.vaadin.ui.component.grid.provider.impl.delay;

import com.learning.model.grid.Delay;
import com.learning.vaadin.ui.component.grid.provider.ColumnProvider;
import com.vaadin.flow.function.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class DelayItemColumnProvider implements ColumnProvider<Delay.DelayItem> {

    @Override
    public Class<Delay.DelayItem> beanType() {
        return Delay.DelayItem.class;
    }

    @Override
    public Map<String, ValueProvider<Delay.DelayItem, ?>> getHeaderAndValueProviders() {
        return new LinkedHashMap<>() {{
            put("Time", Delay.DelayItem::getTime);
            put("Reason", Delay.DelayItem::getReason);
            put("Delay Number", Delay.DelayItem::getDelayNumber);
            put("Is Root Cause", Delay.DelayItem::isRootCause);
            put("Remark", Delay.DelayItem::getRemark);
        }};
    }
}