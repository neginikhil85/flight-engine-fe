package com.learning.vaadin.ui.view.events;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@PageTitle("flight-delay")
@Route(value = "/ws/flight-delete", layout = MainLayout.class)
public class LandingTimeView extends VerticalLayout{
    private final List<LandingTime> landingTimeData = new ArrayList<>();
    private final LandingTimeConverter converter;

    public LandingTimeView(@Value("${websocket.handshake-url.landing-time}") String webSocketConnectionUrl, LandingTimeConverter converter,
                           ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("flight-return-view");
        H1 title = new H1("landing-time Events");
        SearchableGrid<LandingTime> landingTimeGrid = new SearchableGrid<>(LandingTime.class, columnProviderFactory);
        landingTimeGrid.updateItems(landingTimeData);
        landingTimeGrid.setSearchFilters(GridFilterBean.DELAY_DATA.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, landingTimeGrid.getGrid());

        add(title, landingTimeGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<LandingTime> landingTimeGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(landingTimeGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<LandingTime> landingTimeGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    landingTimeData.add(getlandingTimeGrid(message));
                    landingTimeGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private LandingTime getlandingTimeGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), LandingTimeEvent.class));
    }
}
