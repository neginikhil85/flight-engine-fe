package com.learning.vaadin.ui.view.events;

import com.learning.converter.OffBlockTimeConverter;
import com.learning.enums.GridFilterBean;
import com.learning.event.OffBlockTimeEvent;
import com.learning.model.grid.OffBlockTime;
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
public class OffBlockTimeView extends VerticalLayout{
    private final List<OffBlockTime> OffBlockTimeData = new ArrayList<>();
    private final OffBlockTimeConverter converter;

    public OffBlockTimeView(@Value("${websocket.handshake-url.off-block-time}") String webSocketConnectionUrl, OffBlockTimeConverter converter,
                           ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("flight-return-view");
        H1 title = new H1("off-block-time Events");
        SearchableGrid<OffBlockTime> OffBlockTimeGrid = new SearchableGrid<>(OffBlockTime.class, columnProviderFactory);
        OffBlockTimeGrid.updateItems(OffBlockTimeData);
        OffBlockTimeGrid.setSearchFilters(GridFilterBean.DELAY_DATA.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, OffBlockTimeGrid.getGrid());

        add(title, OffBlockTimeGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<OffBlockTime> OffBlockTimeGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(OffBlockTimeGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<OffBlockTime> OffBlockTimeGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    OffBlockTimeData.add(getOffBlockTimeGrid(message));
                    OffBlockTimeGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private OffBlockTime getOffBlockTimeGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), OffBlockTimeEvent.class));
    }
}
