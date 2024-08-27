package com.learning.vaadin.ui.component.form;

import com.learning.enums.FormField;
import com.learning.vaadin.ui.component.picker.VerticalDateTimePicker;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class FlightForm extends FormLayout {

    // Define the TextField components
    VerticalDateTimePicker startDateTime = new VerticalDateTimePicker("Start Date Time");
    VerticalDateTimePicker endDateTime = new VerticalDateTimePicker("End Date Time");
    TextField flightNumber = new TextField("Flight Number");
    TextField seqNumber = new TextField("Seq Number");
    TextField startStation = new TextField("Start Station");
    TextField endStation = new TextField("End Station");
    TextField startTerminal = new TextField("Start Terminal");
    TextField endTerminal = new TextField("End Terminal");

    // Layouts for mandatory and optional fields
    VerticalLayout mandatoryLayout = new VerticalLayout();
    VerticalLayout optionalLayout = new VerticalLayout();

    public FlightForm() {
        addClassName("flight-form");

        // Add fields to their respective layouts
        mandatoryLayout.add(startDateTime, endDateTime);
        optionalLayout.add(flightNumber, seqNumber, startStation, endStation, startTerminal, endTerminal);

        // Create Accordion sections
        Accordion accordion = new Accordion();
        accordion.add("Mandatory", mandatoryLayout);
        accordion.add("Optional", optionalLayout);

        // Add Accordion to the form
        add(accordion);
    }

    public Map<FormField, AbstractField<?,?>> getFields() {
        return new LinkedHashMap<>() {{
            put(FormField.START_DATE_TIME, startDateTime);
            put(FormField.END_DATE_TIME, endDateTime);
            put(FormField.FLIGHT_NUMBER, flightNumber);
            put(FormField.SEQ_NUMBER, seqNumber);
            put(FormField.START_STATION, startStation);
            put(FormField.END_STATION, endStation);
            put(FormField.START_TERMINAL, startTerminal);
            put(FormField.END_TERMINAL, endTerminal);
        }};
    }
}
