package com.learning.vaadin.ui.view.events.ramp;

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
@PageTitle("terminal")
@Route(value = "/ws/terminal", layout = MainLayout.class)
public class TerminalView extends VerticalLayout{
    private final List<Terminal> terminalData = new ArrayList<>();
    private final TerminalConverter converter;

    public TerminalView(@Value("${websocket.handshake-url.terminal}") String webSocketConnectionUrl,
                        TerminalConverter converter, ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("event-view");
        H1 title = new H1("Terminal Events");
        SearchableGrid<Terminal> terminalGrid = new SearchableGrid<>(Terminal.class, columnProviderFactory);
        terminalGrid.updateItems(terminalData);
        terminalGrid.setSearchFilters(GridFilterBean.TERMINAL.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, terminalGrid.getGrid());

        add(title, terminalGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<Terminal> terminalGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(terminalGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<Terminal> terminalGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    terminalData.add(getTerminalGrid(message));
                    terminalGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private Terminal getTerminalGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), TerminalEvent.class));
    }
    
}
