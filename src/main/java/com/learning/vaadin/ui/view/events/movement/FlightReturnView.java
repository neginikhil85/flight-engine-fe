package com.learning.vaadin.ui.view.events.movement;

import com.learning.converter.FlightReturnConverter;
import com.learning.enums.GridFilterBean;
import com.learning.event.FlightReturnEvent;
import com.learning.model.grid.FlightReturn;
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
@PageTitle("flight-return")
@Route(value = "/ws/flight-return", layout = MainLayout.class)
public class FlightReturnView extends VerticalLayout {
    private final List<FlightReturn> flightReturnData = new ArrayList<>();
    private final FlightReturnConverter converter;

    public FlightReturnView(@Value("${websocket.handshake-url.flight-return}") String webSocketConnectionUrl,
                            FlightReturnConverter converter, ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("event-view");
        H1 title = new H1("Flight Return Events");
        SearchableGrid<FlightReturn> flightReturnGrid = new SearchableGrid<>(FlightReturn.class, columnProviderFactory);
        flightReturnGrid.updateItems(flightReturnData);
        flightReturnGrid.setSearchFilters(GridFilterBean.FLIGHT_RETURN.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, flightReturnGrid.getGrid());

        add(title, flightReturnGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<FlightReturn> flightReturnGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(flightReturnGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<FlightReturn> flightReturnGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    flightReturnData.add(getFlightReturnGrid(message));
                    flightReturnGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private FlightReturn getFlightReturnGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), FlightReturnEvent.class));
    }
}
