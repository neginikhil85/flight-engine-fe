package com.learning.vaadin.ui.view.events.times.actual;

import com.learning.converter.LandingTimeConverter;
import com.learning.enums.GridFilterBean;
import com.learning.event.LandingTimeEvent;
import com.learning.model.grid.LandingTime;
import com.learning.util.MapperUtils;
import com.learning.vaadin.ui.component.grid.CustomGrid;
import com.learning.vaadin.ui.component.grid.SearchableGrid;
import com.learning.vaadin.ui.component.grid.provider.ColumnProviderFactory;
import com.learning.vaadin.ui.layout.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@PageTitle("landing-actual-time")
@Route(value = "/ws/landing-actual-time", layout = MainLayout.class)
public class LandingTimeView extends VerticalLayout {
    private final List<LandingTime> landingTimesData = new ArrayList<>();
    private final LandingTimeConverter converter;

    public LandingTimeView(@Value("${websocket.handshake-url.landing-actual-time}") String webSocketConnectionUrl,
                           LandingTimeConverter converter, ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("event-view");
        H1 title = new H1("Landing Actual Time");
        SearchableGrid<LandingTime> landingTimesGrid = new SearchableGrid<>(LandingTime.class, columnProviderFactory);
        landingTimesGrid.updateItems(landingTimesData);
        landingTimesGrid.setSearchFilters(GridFilterBean.LANDING_TIME.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, landingTimesGrid.getGrid());

        add(title, landingTimesGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<LandingTime> landingTimesGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(landingTimesGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<LandingTime> landingTimesGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    landingTimesData.add(getLandingTimeGrid(message));
                    landingTimesGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private LandingTime getLandingTimeGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), LandingTimeEvent.class));
    }
    
}
