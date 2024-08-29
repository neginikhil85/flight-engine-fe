package com.learning.vaadin.ui.view.events.times.estimated;

import com.learning.converter.InBlockTimeConverter;
import com.learning.enums.GridFilterBean;
import com.learning.event.InBlockTimeEvent;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@PageTitle("in-block-estimated-time")
@Route(value = "/ws/in-block-estimated-time", layout = MainLayout.class)
public class InBlockTimeView extends VerticalLayout {
    private final List<InBlockTime> inBlockTimesData = new ArrayList<>();
    private final InBlockTimeConverter converter;

    public InBlockTimeView(@Value("${websocket.handshake-url.in-block-estimated-time}") String webSocketConnectionUrl,
                           InBlockTimeConverter converter, ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("event-view");
        H1 title = new H1("InBlock Estimated Time");
        SearchableGrid<InBlockTime> inBlockTimesGrid = new SearchableGrid<>(InBlockTime.class, columnProviderFactory);
        inBlockTimesGrid.updateItems(inBlockTimesData);
        inBlockTimesGrid.setSearchFilters(GridFilterBean.IN_BLOCK_TIME.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, inBlockTimesGrid.getGrid());

        add(title, inBlockTimesGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<InBlockTime> inBlockTimesGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(inBlockTimesGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<InBlockTime> inBlockTimesGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    inBlockTimesData.add(getInBlockTimeGrid(message));
                    inBlockTimesGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private InBlockTime getInBlockTimeGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), InBlockTimeEvent.class));
    }
    
}
