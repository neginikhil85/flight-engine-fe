package com.learning.vaadin.ui.view.events;

import com.learning.converter.TerminalConverter;
import com.learning.enums.GridFilterBean;
import com.learning.event.TerminalEvent;
import com.learning.model.grid.Terminal;
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
public class TerminalView extends VerticalLayout{
    private final List<Terminal> TerminalData = new ArrayList<>();
    private final TerminalConverter converter;

    public TerminalView(@Value("${websocket.handshake-url.terminal}") String webSocketConnectionUrl, TerminalConverter converter,
                           ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("flight-return-view");
        H1 title = new H1("Terminal Events");
        SearchableGrid<Terminal> TerminalGrid = new SearchableGrid<>(Terminal.class, columnProviderFactory);
        TerminalGrid.updateItems(TerminalData);
        TerminalGrid.setSearchFilters(GridFilterBean.DELAY_DATA.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, TerminalGrid.getGrid());

        add(title, TerminalGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<Terminal> TerminalGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(TerminalGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<Terminal> TerminalGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    TerminalData.add(getTerminalGrid(message));
                    TerminalGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private Terminal getTerminalGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), TerminalEvent.class));
    }
    
}
