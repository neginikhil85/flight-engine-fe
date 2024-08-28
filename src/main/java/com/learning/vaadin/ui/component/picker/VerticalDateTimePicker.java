package com.learning.vaadin.ui.component.picker;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class VerticalDateTimePicker extends CustomField<LocalDateTime> {

    private final DatePicker datePicker;
    private final TimePicker timePicker;

    public VerticalDateTimePicker(String label) {
        addClassName("custom-date-time-picker");

        setLabel(label);
        // Initialize date and time pickers
        datePicker = new DatePicker();
        datePicker.setPlaceholder("select date");
        timePicker = new TimePicker();
        timePicker.setPlaceholder("select time");

        // Add components to the layout
        VerticalLayout layout = new VerticalLayout(datePicker, timePicker);
        layout.setClassName("custom-date-time-picker-container");
        // Add the layout to the custom field
        add(layout);
    }

    @Override
    protected LocalDateTime generateModelValue() {
        return LocalDateTime.of(
                datePicker.getValue(),
                timePicker.getValue() != null ? timePicker.getValue() : LocalTime.MIDNIGHT
        );
    }

    @Override
    protected void setPresentationValue(LocalDateTime value) {
        if (value != null) {
            datePicker.setValue(value.toLocalDate());
            timePicker.setValue(value.toLocalTime());
        } else {
            datePicker.clear();
            timePicker.clear();
        }
    }
}