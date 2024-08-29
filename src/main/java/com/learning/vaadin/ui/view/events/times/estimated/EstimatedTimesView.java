package com.learning.vaadin.ui.view.events.times.estimated;

import com.learning.config.WebSocketHandshakeConfig;
import com.learning.converter.InBlockTimeConverter;
import com.learning.converter.LandingTimeConverter;
import com.learning.converter.OffBlockTimeConverter;
import com.learning.converter.TakeOffTimeConverter;
import com.learning.vaadin.ui.component.grid.provider.ColumnProviderFactory;
import com.learning.vaadin.ui.layout.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@PageTitle("estimated-times")
@Route(value = "/ws/estimated-times", layout = MainLayout.class)
public class EstimatedTimesView extends VerticalLayout {

    public EstimatedTimesView(WebSocketHandshakeConfig handshakeConfig, InBlockTimeConverter inBlockTimeConverter,
                           OffBlockTimeConverter offBlockTimeConverter, TakeOffTimeConverter takeOffTimeConverter,
                           LandingTimeConverter landingTimeConverter, ColumnProviderFactory columnProviderFactory) {

        addClassName("event-view");
        H1 title = new H1("Estimated Times");

        InBlockTimeView inBlockTimeView = new InBlockTimeView(handshakeConfig.getInBlockEstimatedTime(),
                inBlockTimeConverter, columnProviderFactory);
        OffBlockTimeView offBlockTimeView = new OffBlockTimeView(handshakeConfig.getOffBlockEstimatedTime(),
                offBlockTimeConverter, columnProviderFactory);
        TakeoffTimeView takeoffTimeView = new TakeoffTimeView(handshakeConfig.getTakeoffEstimatedTime(),
                takeOffTimeConverter, columnProviderFactory);
        LandingTimeView landingTimeView = new LandingTimeView(handshakeConfig.getLandingEstimatedTime(),
                landingTimeConverter, columnProviderFactory);

        add(title, inBlockTimeView, offBlockTimeView, takeoffTimeView, landingTimeView);
    }
}
