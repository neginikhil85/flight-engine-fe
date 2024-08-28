package com.learning.vaadin.ui.view.events;

import com.learning.converter.GateChangeConverter;
import com.learning.converter.InBlockTimeConverter;
import com.learning.enums.GridFilterBean;
import com.learning.event.GateChangeEvent;
import com.learning.event.InBlockTimeEvent;
import com.learning.model.grid.GateChange;
import com.learning.model.grid.InBlockTime;
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
public class InBlockTimeView extends VerticalLayout{
    private final List<InBlockTime> inBlockTimeData = new ArrayList<>();
    private final InBlockTimeConverter converter;

    public InBlockTimeView(@Value("${websocket.handshake-url.in-block-time}") String webSocketConnectionUrl, InBlockTimeConverter converter,
                          ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("flight-return-view");
        H1 title = new H1("in-block Events");
        SearchableGrid<InBlockTime> inBlockTimeGrid = new SearchableGrid<>(InBlockTime.class, columnProviderFactory);
        inBlockTimeGrid.updateItems(inBlockTimeData);
        inBlockTimeGrid.setSearchFilters(GridFilterBean.DELAY_DATA.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, inBlockTimeGrid.getGrid());

        add(title, inBlockTimeGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<InBlockTime> inBlockTimeGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(inBlockTimeGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<InBlockTime> inBlockTimeGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    inBlockTimeData.add(getinBlockTimeGrid(message));
                    inBlockTimeGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private InBlockTime getinBlockTimeGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), InBlockTimeEvent.class));
    }
}
