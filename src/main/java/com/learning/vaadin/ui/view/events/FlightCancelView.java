package com.learning.vaadin.ui.view.events;

import com.learning.converter.FlightCancelConverter;
import com.learning.enums.GridFilterBean;
import com.learning.event.FlightCancelEvent;
import com.learning.model.grid.FlightCancel;
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
@PageTitle("flight-cancel")
@Route(value = "/ws/flight-cancel", layout = MainLayout.class)
public class FlightCancelView extends VerticalLayout {
    private final List<FlightCancel> flightCancelData = new ArrayList<>();
    private final FlightCancelConverter converter;

    public FlightCancelView(@Value("${websocket.handshake-url.flight-cancel}") String webSocketConnectionUrl,
                            FlightCancelConverter converter, ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("event-view");
        H1 title = new H1("Flight Cancel Events");
        SearchableGrid<FlightCancel> flightCancelGrid = new SearchableGrid<>(FlightCancel.class, columnProviderFactory);
        flightCancelGrid.updateItems(flightCancelData);
        flightCancelGrid.setSearchFilters(GridFilterBean.FLIGHT_CANCEL.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, flightCancelGrid.getGrid());

        add(title, flightCancelGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<FlightCancel> flightCancelGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(flightCancelGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<FlightCancel> flightCancelGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    flightCancelData.add(getFlightCancelGrid(message));
                    flightCancelGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private FlightCancel getFlightCancelGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), FlightCancelEvent.class));
    }
}
