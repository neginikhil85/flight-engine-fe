package com.learning.util;

import com.learning.vaadin.ui.component.picker.VerticalDateTimePicker;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@UtilityClass
public class CommonUtils {

    private final String pattern = "dd-MM-yyyy hh:mm a";

    public static String getTextFieldValue(Component field) {
        return  ((field instanceof TextField textField) && StringUtils.isNotBlank(textField.getValue()))
                ? textField.getValue()
                : null ;
    }

    public static String getDateFieldValue(Component field) {
        return ((field instanceof VerticalDateTimePicker dateTimePicker)) && Objects.nonNull(dateTimePicker.getValue())
                ? dateTimePicker.getValue().toString()
                : null;
    }

    public static Integer convertToInteger(Component textField) {
        return Optional.ofNullable(getTextFieldValue(textField))
                .map(Integer::valueOf)
                .orElse(null);
    }

    public static String getFormattedDate(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static  <T> void setIfNotNull(T t, Consumer<T> consumer) {
        Optional.ofNullable(t).ifPresent(consumer);
    }
}
