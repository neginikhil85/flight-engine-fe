package com.learning.vaadin.ui.component.grid;

import com.learning.vaadin.ui.component.grid.filter.GridFilters;
import com.learning.vaadin.ui.component.grid.provider.ColumnProviderFactory;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.Getter;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class SearchableGrid<T> extends VerticalLayout {
    @Getter
    private final CustomGrid<T> grid;
    private GridListDataView<T> gridListDataView;
    private GridFilters<T> gridFilters;

    public SearchableGrid(Class<T> beanType, ColumnProviderFactory columnProviderFactory) {
        addClassName("searchable-grid");

        this.grid = new CustomGrid<>(beanType, columnProviderFactory);

        TextField searchField = new TextField();
        searchField.setPlaceholder("search");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(event -> {
            Optional.ofNullable(gridListDataView)
                    .ifPresent(dataListView -> {
                        gridListDataView.removeFilters();
                        dataListView.refreshAll();
                        applyFilter(searchField.getValue().trim());
                    });;
        });

        add(searchField, grid);
    }

    private void applyFilter(String searchTerm) {
        if (Objects.nonNull(gridListDataView) && Objects.nonNull(gridFilters)) {
            gridListDataView.addFilter(gridFilters.getFilters(searchTerm));
        }
    }

    public void updateItems(Collection<T> data) {
        this.gridListDataView = grid.updateItems(data);
    }

    public void setSearchFilters(GridFilters<T> gridFilters) {
        this.gridFilters = gridFilters;
    }

}
