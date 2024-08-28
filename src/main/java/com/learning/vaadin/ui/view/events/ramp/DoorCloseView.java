package com.learning.vaadin.ui.view.events.ramp;

import com.learning.converter.DoorCloseConverter;
import com.learning.enums.GridFilterBean;
import com.learning.event.DoorCloseEvent;
import com.learning.model.grid.DoorClose;
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
@PageTitle("door-close")
@Route(value = "/ws/door-close", layout = MainLayout.class)
public class DoorCloseView extends VerticalLayout{

    private final List<DoorClose> doorCloseData = new ArrayList<>();
    private final DoorCloseConverter converter;

    public DoorCloseView(@Value("${websocket.handshake-url.door-close}") String webSocketConnectionUrl,
                         DoorCloseConverter converter, ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("event-view");
        H1 title = new H1("Door close Events");
        SearchableGrid<DoorClose> doorCloseGrid = new SearchableGrid<>(DoorClose.class, columnProviderFactory);
        doorCloseGrid.updateItems(doorCloseData);
        doorCloseGrid.setSearchFilters(GridFilterBean.DOOR_CLOSE.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, doorCloseGrid.getGrid());

        add(title, doorCloseGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<DoorClose> doorCloseGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(doorCloseGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<DoorClose> doorCloseGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    doorCloseData.add(getDoorCloseGrid(message));
                    doorCloseGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private DoorClose getDoorCloseGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), DoorCloseEvent.class));
    }
}
