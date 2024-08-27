package com.learning.vaadin.ui.component.grid;


import com.learning.vaadin.ui.component.grid.provider.ColumnProvider;
import com.learning.vaadin.ui.component.grid.provider.ColumnProviderFactory;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.function.ValueProvider;

import java.util.Collection;
import java.util.List;

public class CustomGrid<T> extends Grid<T> {

    private final ColumnProviderFactory columnProviderFactory;
    private final ColumnProvider<T> columnProvider;

    public CustomGrid(Class<T> beanType, ColumnProviderFactory columnProviderFactory) {
        super(beanType, false);
        this.columnProviderFactory = columnProviderFactory;
        this.columnProvider = columnProvider(beanType);

        initializeGrid(beanType);
    }

    private ColumnProvider<T> columnProvider(Class<T> beanType) {
        return columnProviderFactory.getProvider(beanType);
    }

    private void initializeGrid(Class<T> beanType) {
        // Initialize columns based on the ColumnProvider
        columnProvider.getHeaderAndValueProviders()
                .forEach(this::addColumns);

        // Add custom columns if provided
        List<ColumnProvider.CustomColumn<T>> customColumns = columnProvider.getCustomColumns(columnProviderFactory);
        customColumns.forEach(this::addColumns);

        // Common setup for all grids
        addThemeVariants(GridVariant.LUMO_COMPACT);
        addClassName("custom-grid");
    }

    private void addColumns(String header, ValueProvider<T, ?> valueProvider) {
        Column<T> column = addColumn(valueProvider)
                .setHeader(header)
                .setAutoWidth(true);

        if (header.equals("Event Received On")) {
            this.sort(List.of(new GridSortOrder<>(column, SortDirection.DESCENDING)));
            column.setVisible(false); // comment this if you need to show the column
        }
    }

    private void addColumns(ColumnProvider.CustomColumn<T> customColumn) {
        addColumn(customColumn.getRenderer())
                .setHeader(customColumn.getHeader())
                .setAutoWidth(true);
    }

    public GridListDataView<T> updateItems(Collection<T> data) {
        return super.setItems(data);
    }

}