package com.learning.vaadin.ui.view.events.movement;

import com.learning.converter.DelayConverter;
import com.learning.enums.GridFilterBean;
import com.learning.event.DelayEvent;
import com.learning.model.grid.Delay;
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
@PageTitle("delay")
@Route(value = "/ws/delay", layout = MainLayout.class)
public class DelayView extends VerticalLayout {

    private final List<Delay> delayData = new ArrayList<>();
    private final DelayConverter converter;

    public DelayView(@Value("${websocket.handshake-url.delay}") String webSocketConnectionUrl, DelayConverter converter,
                     ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("event-view");
        H1 title = new H1("Delay Events");
        SearchableGrid<Delay> delayGrid = new SearchableGrid<>(Delay.class, columnProviderFactory);
        delayGrid.updateItems(delayData);
        delayGrid.setSearchFilters(GridFilterBean.DELAY.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, delayGrid.getGrid());

        add(title, delayGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<Delay> delayGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(delayGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<Delay> delayGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    delayData.add(getDelayGrid(message));
                    delayGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private Delay getDelayGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), DelayEvent.class));
    }
}
