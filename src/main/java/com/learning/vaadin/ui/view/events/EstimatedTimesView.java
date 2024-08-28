package com.learning.vaadin.ui.view.events;

import com.learning.enums.GridFilterBean;
import com.learning.model.grid.ActualTimes;
import com.learning.vaadin.ui.component.grid.SearchableGrid;
import com.learning.vaadin.ui.component.grid.provider.ColumnProviderFactory;
import com.learning.vaadin.ui.layout.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@PageTitle("estimated-times")
@Route(value = "/ws/estimated-times", layout = MainLayout.class)
public class EstimatedTimesView extends VerticalLayout {

    private final List<ActualTimes> actualTimesData  = new ArrayList<>();

    public EstimatedTimesView(@Value("${websocket.handshake-url.in-block-time}") String webSocketConnectionUrl,
                              ColumnProviderFactory columnProviderFactory) {

        addClassName("event-view");
        H1 title = new H1("Estimated Times Events");
        SearchableGrid<ActualTimes> estimatedTimes = new SearchableGrid<>(ActualTimes.class, columnProviderFactory);
        estimatedTimes.updateItems(actualTimesData);
        estimatedTimes.setSearchFilters(GridFilterBean.ESTIMATED_TIMES.getBean());

        add(title, estimatedTimes);
    }
}
