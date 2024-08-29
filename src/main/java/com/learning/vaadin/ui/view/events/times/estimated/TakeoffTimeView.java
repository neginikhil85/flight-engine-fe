package com.learning.vaadin.ui.view.events.times.estimated;

import com.learning.converter.TakeOffTimeConverter;
import com.learning.enums.GridFilterBean;
import com.learning.event.TakeOffTimeEvent;
import com.learning.model.grid.TakeOffTime;
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

@PageTitle("takeoff-estimated-time")
@Route(value = "/ws/takeoff-estimated-time", layout = MainLayout.class)
public class TakeoffTimeView extends VerticalLayout {
    private final List<TakeOffTime> takeOffTimesData = new ArrayList<>();
    private final TakeOffTimeConverter converter;

    public TakeoffTimeView(@Value("${websocket.handshake-url.takeoff-estimated-time}") String webSocketConnectionUrl,
                           TakeOffTimeConverter converter, ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("event-view");
        H1 title = new H1("Takeoff Time");
        SearchableGrid<TakeOffTime> takeOffTimeGrid = new SearchableGrid<>(TakeOffTime.class, columnProviderFactory);
        takeOffTimeGrid.updateItems(takeOffTimesData);
        takeOffTimeGrid.setSearchFilters(GridFilterBean.TAKE_OFF_TIME.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, takeOffTimeGrid.getGrid());

        add(title, takeOffTimeGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<TakeOffTime> takeOffTimeGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(takeOffTimeGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<TakeOffTime> takeOffTimeGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    takeOffTimesData.add(getTakeoffTimeGrid(message));
                    takeOffTimeGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private TakeOffTime getTakeoffTimeGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), TakeOffTimeEvent.class));
    }
    
}
