package com.learning.vaadin.ui.view.events.movement;

import com.learning.converter.EquipmentConverter;
import com.learning.enums.GridFilterBean;
import com.learning.event.EquipmentEvent;
import com.learning.model.grid.Equipment;
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
@PageTitle("equipment")
@Route(value = "/ws/equipment", layout = MainLayout.class)
public class EquipmentView extends VerticalLayout {
    private final List<Equipment> equipmentData = new ArrayList<>();
    private final EquipmentConverter converter;

    public EquipmentView(@Value("${websocket.handshake-url.equipment}") String webSocketConnectionUrl,
                         EquipmentConverter converter, ColumnProviderFactory columnProviderFactory) {
        this.converter = converter;

        addClassName("event-view");
        H1 title = new H1("Equipment Events");
        SearchableGrid<Equipment> equipmentGrid = new SearchableGrid<>(Equipment.class, columnProviderFactory);
        equipmentGrid.updateItems(equipmentData);
        equipmentGrid.setSearchFilters(GridFilterBean.EQUIPMENT.getBean());

        doWebSocketHandshake(webSocketConnectionUrl, equipmentGrid.getGrid());

        add(title, equipmentGrid);
    }

    private void doWebSocketHandshake(String webSocketConnectionUrl, CustomGrid<Equipment> equipmentGrid) {
        WebSocketClient client = new StandardWebSocketClient();
        client.execute(getHandler(equipmentGrid), webSocketConnectionUrl);
    }

    private AbstractWebSocketHandler getHandler(Grid<Equipment> equipmentGrid) {
        return new AbstractWebSocketHandler() {

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    equipmentData.add(getEquipmentGrid(message));
                    equipmentGrid.getDataProvider().refreshAll();
                }));
            }
        };
    }

    private Equipment getEquipmentGrid(WebSocketMessage<?> message) {
        return converter.convert(MapperUtils.readValue(message.getPayload().toString(), EquipmentEvent.class));
    }
}
