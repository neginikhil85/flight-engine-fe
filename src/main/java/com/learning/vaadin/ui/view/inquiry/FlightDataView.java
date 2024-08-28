package com.learning.vaadin.ui.view.inquiry;

import com.learning.enums.GridFilterBean;
import com.learning.model.response.FlightLegState;
import com.learning.vaadin.ui.component.grid.SearchableGrid;
import com.learning.vaadin.ui.component.grid.provider.ColumnProviderFactory;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class FlightDataView extends VerticalLayout {

    private final SearchableGrid<FlightLegState> flightInfoGrid;

    public FlightDataView(ColumnProviderFactory columnProviderFactory) {
        addClassName("data-view");

        H1 title = new H1("Flight Data");

        flightInfoGrid = new SearchableGrid<>(FlightLegState.class, columnProviderFactory);
        flightInfoGrid.setSearchFilters(GridFilterBean.FLIGHT_LEG.getBean());

        add(title, flightInfoGrid);
        setSizeFull();
        setFlexGrow(1, flightInfoGrid);
    }

    public void updateGridData(List<FlightLegState> flightLegStateData) {
        flightInfoGrid.updateItems(flightLegStateData);
    }
}
