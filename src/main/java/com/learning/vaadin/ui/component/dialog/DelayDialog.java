package com.learning.vaadin.ui.component.dialog;

import com.learning.model.grid.Delay;
import com.learning.vaadin.ui.component.grid.CustomGrid;
import com.learning.vaadin.ui.component.grid.provider.ColumnProviderFactory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class DelayDialog extends Dialog {

    CustomGrid<Delay.DelayItem> nestedGrid;

    public DelayDialog(List<Delay.DelayItem> delayItems, ColumnProviderFactory factory) {
        setDraggable(true);

        Div headerTitle = new Div();
        headerTitle.setText("Delays");
        headerTitle.addClassName("dialog-title");

        Button closeBtn = new Button();
        closeBtn.addClickListener(click -> this.close());
        closeBtn.addClassName("dialog-close-btn");
        closeBtn.setIcon(VaadinIcon.CLOSE.create());

        HorizontalLayout headerLayout = new HorizontalLayout(headerTitle, closeBtn);
        headerLayout.addClassName("dialog-header");
        headerLayout.setWidthFull();

        // Create a nested grid to show delay items
        nestedGrid = new CustomGrid<>(Delay.DelayItem.class, factory);
        nestedGrid.addClassName("nested-custom-grid");
        if (!CollectionUtils.isEmpty(delayItems)) {
            nestedGrid.setItems(delayItems);
        }

        // Add the grid to the dialog
        add(headerLayout, nestedGrid);
    }
}