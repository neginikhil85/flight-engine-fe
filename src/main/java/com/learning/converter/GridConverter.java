package com.learning.converter;

import java.util.List;

public interface GridConverter<T, S> {

    S convert(T t);
    List<S> convert(List<T> t);
}
