package com.learning.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class MapperUtils {

    private final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    public <T> T readValue(String jsonStr, Class<T> type) {
        try {
           return objectMapper.readValue(jsonStr, type);
        } catch (Exception e) {
            log.error("[MapperUtils] Error while parsing json string", e);
        }
        return null;
    }

    public <T> T convert(Object obj, Class<T> type) {
        try {
            return objectMapper.convertValue(obj, type);
        } catch (Exception e) {
            log.error("[MapperUtils] Error while converting obj to given type", e);
        }
        return null;
    }

    public <T> T convert(Object obj, TypeReference<T> type) {
        try {
            return objectMapper.convertValue(obj, type);
        } catch (Exception e) {
            log.error("[MapperUtils] Error while converting obj to given type", e);
        }
        return null;
    }


    public String convert(Object data) {
        try {
           return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            log.error("[MapperUtils] Error while converting java obj to string", e);
        }
        return null;
    }
}
