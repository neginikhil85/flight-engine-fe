package com.learning.vaadin.ui.view.events;

import com.learning.converter.TakeOffTimeConverter;
import com.learning.enums.GridFilterBean;
import com.learning.event.TakeOffTimeEvent;
import com.learning.model.grid.TakeOffTime;
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
public class TakeOffTimeView extends VerticalLayout{
    private final List<TakeOffTime> TakeOffTimeData = new ArrayList<>();
    private final TakeOffTimeConverter converter;

    public TakeOffTimeView(@Value("${websocket.handshake-url.takTakeoff-time}") String webSocketConnectionUrl, TakeOffTimeConverter converter,
                            ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("flight-return-view");
        H1 title = new H1("take-off-time Events");
        SearchableGrid<TakeOffTime> TakeOffTimeGrid = new SearchableGrid<>(TakeOffTime.class, columnProviderFactory);
        TakeOffTimeGrid.updateItems(TakeOffTimeData);
        TakeOffTimeGrid.setSearchFilters(GridFilterBean.DELAY_DATA.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, TakeOffTimeGrid.getGrid());

        add(title, TakeOffTimeGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<TakeOffTime> TakeOffTimeGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(TakeOffTimeGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<TakeOffTime> TakeOffTimeGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    TakeOffTimeData.add(getTakeOffTimeGrid(message));
                    TakeOffTimeGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private TakeOffTime getTakeOffTimeGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), TakeOffTimeEvent.class));
    }
}
