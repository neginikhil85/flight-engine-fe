package com.learning.vaadin.ui.view.events.ramp;

import com.learning.converter.GateChangeConverter;
import com.learning.enums.GridFilterBean;
import com.learning.event.GateChangeEvent;
import com.learning.model.grid.GateChange;
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
@PageTitle("gate-change")
@Route(value = "/ws/gate-change", layout = MainLayout.class)
public class GateChangeView extends VerticalLayout{
    private final List<GateChange> gateChangeData = new ArrayList<>();
    private final GateChangeConverter converter;

    public GateChangeView(@Value("${websocket.handshake-url.gate-change}") String webSocketConnectionUrl,
                          GateChangeConverter converter, ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("event-view");
        H1 title = new H1("Gate Change Events");
        SearchableGrid<GateChange> gateChangeGrid = new SearchableGrid<>(GateChange.class, columnProviderFactory);
        gateChangeGrid.updateItems(gateChangeData);
        gateChangeGrid.setSearchFilters(GridFilterBean.GATE_CHANGE.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, gateChangeGrid.getGrid());

        add(title, gateChangeGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<GateChange> gateChangeGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(gateChangeGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<GateChange> gateChangeGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    gateChangeData.add(getGateChangeGrid(message));
                    gateChangeGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private GateChange getGateChangeGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), GateChangeEvent.class));
    }
    
}
