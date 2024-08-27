package com.learning.vaadin.ui.view.inquiry;

import com.learning.model.request.FlightRequest;
import com.learning.model.response.FlightLegState;
import com.learning.model.response.FlightResponse;
import com.learning.service.JocService;
import com.learning.vaadin.ui.component.grid.provider.ColumnProviderFactory;
import com.learning.vaadin.ui.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Route(value = "/api/flight-inquiry", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class FlightInquiryView extends VerticalLayout {

    private final JocService jocService;
    private FlightFormView formView;
    private Button toggleButton;
    private FlightDataView dataView;

    @Setter
    @Getter
    private FlightRequest flightRequest;

    public FlightInquiryView(ColumnProviderFactory columnProviderFactory, JocService jocService) {
        this.jocService = jocService;

        setClassName("flight-inquiry-layout");
        setSizeFull();

        // Create header layout
        Div header = createHeaderLayout();

        // Create contents layout
        Div contents = createContentLayout(columnProviderFactory);

        makeViewResponsive();

        add(header, contents);
    }

    private void makeViewResponsive() {
        try {
            if (formView.isVisible() && Double.parseDouble(this.getWidth()) < 1300) {
                formView.setVisible(false);
                toggleButton.setText("Show Form");
            }
        } catch (Exception ignored) {}
    }

    private Div createHeaderLayout() {
        H1 viewTitle = new H1("Flight Inquiry");
        toggleButton = new Button("Hide Form", event -> toggleFormVisibility());
        Div header = new Div(viewTitle, toggleButton);
        header.addClassName("header");
        return header;
    }

    private Div createContentLayout(ColumnProviderFactory columnProviderFactory) {
        dataView = new FlightDataView(columnProviderFactory);
        formView = new FlightFormView(jocService);
        Div contents = new Div(dataView, formView);
        contents.addClassName("contents");
        return contents;
    }

    private void toggleFormVisibility() {
        boolean isFormVisible = formView.isVisible();
        formView.setVisible(!isFormVisible);
        toggleButton.setText(isFormVisible ? "Show Form" : "Hide Form");
    }

    public void setFlightResponseInGrid(FlightResponse flightResponse) {
        List<FlightLegState> flightLegStates = flightResponse.getFlightDocumentList().stream()
                .map(FlightResponse.FlightDocument::getFlightLegState)
                .collect(Collectors.toList());

        dataView.updateGridData(flightLegStates);
    }

}


