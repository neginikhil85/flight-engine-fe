package com.learning.vaadin.ui.component.grid.provider;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ColumnProviderFactory {
    private final Map<Class<?>, ColumnProvider<?>> beanTypeAndColumnProviderMap = new HashMap<>();

    public ColumnProviderFactory(List<ColumnProvider<?>> columnProviders) {
        columnProviders.forEach(cp -> beanTypeAndColumnProviderMap.put(cp.beanType(), cp));
    }

    @SuppressWarnings("unchecked")
    public <T> ColumnProvider<T> getProvider(Class<T> beanType) {
        return (ColumnProvider<T>) beanTypeAndColumnProviderMap.get(beanType);
    }
}
