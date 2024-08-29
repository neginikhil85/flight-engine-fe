package com.learning.vaadin.ui.view.events.times.actual;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@PageTitle("off-block-actual-time")
@Route(value = "/ws/off-block-actual-time", layout = MainLayout.class)
public class OffBlockTimeView extends VerticalLayout {
    private final List<OffBlockTime> offBlockTimesData = new ArrayList<>();
    private final OffBlockTimeConverter converter;

    public OffBlockTimeView(@Value("${websocket.handshake-url.off-block-actual-time}") String webSocketConnectionUrl,
                            OffBlockTimeConverter converter, ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("event-view");
        H1 title = new H1("OffBlock Time");
        SearchableGrid<OffBlockTime> offBlockTimesGrid = new SearchableGrid<>(OffBlockTime.class, columnProviderFactory);
        offBlockTimesGrid.updateItems(offBlockTimesData);
        offBlockTimesGrid.setSearchFilters(GridFilterBean.OFF_BLOCK_TIME.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, offBlockTimesGrid.getGrid());

        add(title, offBlockTimesGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<OffBlockTime> offBlockTimesGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(offBlockTimesGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<OffBlockTime> offBlockTimesGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    offBlockTimesData.add(getOffBlockTimeGrid(message));
                    offBlockTimesGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private OffBlockTime getOffBlockTimeGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), OffBlockTimeEvent.class));
    }
    
}
