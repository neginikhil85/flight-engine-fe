package com.learning.vaadin.ui.component.grid.provider;

import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.ValueProvider;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface ColumnProvider<T> {

    Class<T> beanType();

    Map<String, ValueProvider<T, ?>> getHeaderAndValueProviders();

    // Method to provide multiple custom column renderers
    default List<CustomColumn<T>> getCustomColumns(ColumnProviderFactory factory) {
        return Collections.emptyList();
    }

    // Nested class to represent a custom column
    @Getter
    class CustomColumn<T> {
        private final String header;
        private final Renderer<T> renderer;

        public CustomColumn(String header, Renderer<T> renderer) {
            this.header = header;
            this.renderer = renderer;
        }

    }

    default <T, R> ValueProvider<T, R> safeValueProvider(Function<T, R> mapper) {
        return t -> {
            try {
                return mapper.apply(t);
            } catch (NullPointerException e) {
                return null;
            }
        };
    }


}
