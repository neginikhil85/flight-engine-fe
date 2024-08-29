package com.learning.vaadin.ui.view.events.times.actual;

import com.learning.config.WebSocketHandshakeConfig;
import com.learning.converter.InBlockTimeConverter;
import com.learning.converter.LandingTimeConverter;
import com.learning.converter.OffBlockTimeConverter;
import com.learning.converter.TakeOffTimeConverter;
import com.learning.vaadin.ui.component.grid.provider.ColumnProviderFactory;
import com.learning.vaadin.ui.layout.MainLayout;
import com.learning.vaadin.ui.view.events.times.estimated.InBlockTimeView;
import com.learning.vaadin.ui.view.events.times.estimated.LandingTimeView;
import com.learning.vaadin.ui.view.events.times.estimated.OffBlockTimeView;
import com.learning.vaadin.ui.view.events.times.estimated.TakeoffTimeView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@PageTitle("actual-times")
@Route(value = "/ws/actual-times", layout = MainLayout.class)
public class ActualTimesView extends VerticalLayout {

    public ActualTimesView(WebSocketHandshakeConfig handshakeConfig, InBlockTimeConverter inBlockTimeConverter,
                           OffBlockTimeConverter offBlockTimeConverter, TakeOffTimeConverter takeOffTimeConverter,
                           LandingTimeConverter landingTimeConverter, ColumnProviderFactory columnProviderFactory) {

        addClassName("event-view");
        H1 title = new H1("Actual Times");

        InBlockTimeView inBlockTimeView = new InBlockTimeView(handshakeConfig.getInBlockActualTime(),
                inBlockTimeConverter, columnProviderFactory);
        OffBlockTimeView offBlockTimeView = new OffBlockTimeView(handshakeConfig.getOffBlockActualTime(),
                offBlockTimeConverter, columnProviderFactory);
        TakeoffTimeView takeoffTimeView = new TakeoffTimeView(handshakeConfig.getTakeoffActualTime(),
                takeOffTimeConverter, columnProviderFactory);
        LandingTimeView landingTimeView = new LandingTimeView(handshakeConfig.getLandingActualTime(),
                landingTimeConverter, columnProviderFactory);

        add(title, inBlockTimeView, offBlockTimeView, takeoffTimeView, landingTimeView);
    }
}
