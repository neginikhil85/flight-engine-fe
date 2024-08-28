package com.learning.vaadin.ui.view.events;

import com.learning.converter.FlightDeleteConverter;
import com.learning.enums.GridFilterBean;
import com.learning.event.FlightDeleteEvent;
import com.learning.model.grid.FlightDelete;
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
public class FlightDeleteView extends VerticalLayout {
    private final List<FlightDelete> flightDeleteData = new ArrayList<>();
    private final FlightDeleteConverter converter;

    public FlightDeleteView(@Value("${websocket.handshake-url.flight-delete}") String webSocketConnectionUrl, FlightDeleteConverter converter,
                            ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("flight-cancel-view");
        H1 title = new H1("Flight-Cancel Events");
        SearchableGrid<FlightDelete> flightDeleteGrid = new SearchableGrid<>(FlightDelete.class, columnProviderFactory);
        flightDeleteGrid.updateItems(flightDeleteData);
        flightDeleteGrid.setSearchFilters(GridFilterBean.FLIGHT_DELETE.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, flightDeleteGrid.getGrid());

        add(title, flightDeleteGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<FlightDelete> flightDeleteGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(flightDeleteGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<FlightDelete> flightDeleteGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    flightDeleteData.add(getflightDeleteGrid(message));
                    flightDeleteGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private FlightDelete getflightDeleteGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), FlightDeleteEvent.class));
    }
}
