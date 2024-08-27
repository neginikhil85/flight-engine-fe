package com.learning.vaadin.ui.component.grid.filter;

import com.vaadin.flow.function.SerializablePredicate;

import java.util.Optional;
import java.util.function.Function;

public interface GridFilters<T> {

    SerializablePredicate<T> getFilters(String searchTerm);

    default boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }

    default boolean safeValueMatcher(T object, Function<T, ?> valueProvider, String searchTerm) {
        try {
            return Optional.ofNullable(object)
                    .map(valueProvider)
                    .map(String::valueOf)
                    .map(value -> matchesTerm(value, searchTerm))
                    .orElse(false);
        } catch (Exception e) {
            return false;
        }
    }
}
