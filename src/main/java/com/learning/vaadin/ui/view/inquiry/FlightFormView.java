package com.learning.vaadin.ui.view.inquiry;

import com.learning.enums.FormField;
import com.learning.model.request.FlightRequest;
import com.learning.service.JocService;
import com.learning.util.CommonUtils;
import com.learning.vaadin.ui.component.form.FlightForm;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.Map;

@Slf4j
public class FlightFormView extends VerticalLayout {

    public static final String MANDATORY_FIELD_MISSING = "Mandatory Field missing";
    private final JocService jocService;
    private final FlightForm flightForm;
    private final Button queryBtn;

    public FlightFormView(JocService jocService) {
        this.jocService = jocService;

        addClassName("form-view");

        Div title = new Div("New Flight Query");
        title.setClassName("form-view-title");

        flightForm = new FlightForm();
        queryBtn = new Button("Query");

        queryBtn.addClickListener(click -> onSubmit());
        queryBtn.addClickShortcut(Key.ENTER);

        add(title, flightForm, queryBtn);
    }

    private void onSubmit() {
        boolean mandatoryFieldNull = isMandatoryFieldNull(flightForm.getStartDateTime(), flightForm.getEndDateTime());
        if (mandatoryFieldNull) {
            showError(MANDATORY_FIELD_MISSING);
        } else {
            FlightRequest flightRequest = buildFlightRequest(flightForm.getFields());
            log.info("[FlightFormView::onSubmit] flightRequest: {}", flightRequest);
            jocService.getFlightResponse(flightRequest)
                    .ifPresent(flightResponse -> {
                        UI.getCurrent().navigate(FlightInquiryView.class)
                                .ifPresent(flightView -> {
                                    flightView.setFlightResponseInGrid(flightResponse);
                                    flightView.setFlightRequest(flightRequest);
                                });
                    });
        }

        flightForm.getFields().values().forEach(HasValue::clear);
    }

    private static FlightRequest buildFlightRequest(Map<FormField, AbstractField<?,?>> formTextFields) {
        return FlightRequest.builder()
                .flightNumber(CommonUtils.convertToInteger(formTextFields.get(FormField.FLIGHT_NUMBER)))
                .seqNumber(CommonUtils.convertToInteger(formTextFields.get(FormField.SEQ_NUMBER)))
                .startStation(CommonUtils.getTextFieldValue(formTextFields.get(FormField.START_STATION)))
                .endStation(CommonUtils.getTextFieldValue(formTextFields.get(FormField.END_STATION)))
                .startTerminal(CommonUtils.getTextFieldValue(formTextFields.get(FormField.START_TERMINAL)))
                .startDateTime(CommonUtils.getDateFieldValue(formTextFields.get(FormField.START_DATE_TIME)))
                .endDateTime(CommonUtils.getDateFieldValue(formTextFields.get(FormField.END_DATE_TIME)))
                .operationalStatus(CommonUtils.getTextFieldValue(formTextFields.get(FormField.OPERATIONAL_STATUS)))
                .page(1)
                .size(100)
                .build();
    }

    private boolean isMandatoryFieldNull(AbstractField<?,?>... abstractFields) {
        return Arrays.stream(abstractFields).map(AbstractField::getValue).anyMatch(ObjectUtils::isEmpty);
    }

    private static void showError(String text) {
        Notification errorNotify = new Notification(text);
        errorNotify.addThemeVariants(NotificationVariant.LUMO_ERROR);
        errorNotify.setPosition(Notification.Position.TOP_END);
        errorNotify.setDuration(200000);
        errorNotify.open();
    }
}
