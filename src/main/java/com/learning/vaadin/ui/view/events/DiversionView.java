package com.learning.vaadin.ui.view.events;

import com.learning.converter.DelayConverter;
import com.learning.converter.DiversionConverter;
import com.learning.enums.GridFilterBean;
import com.learning.event.DelayEvent;
import com.learning.event.DiversionEvent;
import com.learning.model.grid.Delay;
import com.learning.model.grid.Diversion;
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
@Route(value = "/ws/flight-delay", layout = MainLayout.class)
public class DiversionView extends VerticalLayout{
    private final List<Diversion> diversionData = new ArrayList<>();
    private final DiversionConverter converter;

    public DiversionView(@Value("${websocket.handshake-url.diversion}") String webSocketConnectionUrl, DiversionConverter converter,
                     ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("delay-view");
        H1 title = new H1("Diversion Events");
        SearchableGrid<Diversion> diversionGrid = new SearchableGrid<>(Diversion.class, columnProviderFactory);
        diversionGrid.updateItems(diversionData);
        diversionGrid.setSearchFilters(GridFilterBean.DIVERSION_DATA.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, diversionGrid.getGrid());

        add(title, diversionGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<Diversion> diversionGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(diversionGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<Diversion> delayGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    diversionData.add(getDiversionGrid(message));
                    delayGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private Diversion getDiversionGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), DiversionEvent.class));
    }
}
