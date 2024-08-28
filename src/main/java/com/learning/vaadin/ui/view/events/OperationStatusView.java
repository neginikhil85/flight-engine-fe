package com.learning.vaadin.ui.view.events;

import com.learning.converter.OperationStatusConverter;
import com.learning.enums.GridFilterBean;
import com.learning.event.OperationStatusEvent;
import com.learning.model.grid.OperationStatus;
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
public class OperationStatusView extends VerticalLayout{
    private final List<OperationStatus> OperationStatusData = new ArrayList<>();
    private final OperationStatusConverter converter;

    public OperationStatusView(@Value("${websocket.handshake-url.operation-status}") String webSocketConnectionUrl, OperationStatusConverter converter,
                            ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("flight-return-view");
        H1 title = new H1("operation-status Events");
        SearchableGrid<OperationStatus> OperationStatusGrid = new SearchableGrid<>(OperationStatus.class, columnProviderFactory);
        OperationStatusGrid.updateItems(OperationStatusData);
        OperationStatusGrid.setSearchFilters(GridFilterBean.DELAY_DATA.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, OperationStatusGrid.getGrid());

        add(title, OperationStatusGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<OperationStatus> OperationStatusGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(OperationStatusGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<OperationStatus> OperationStatusGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    OperationStatusData.add(getOperationStatusGrid(message));
                    OperationStatusGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private OperationStatus getOperationStatusGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), OperationStatusEvent.class));
    }
    
}
