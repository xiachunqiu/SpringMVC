package com.x.y.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public final class ObjectMapperFactory extends ObjectMapper {
    private static ObjectMapper objectMapper;

    private ObjectMapperFactory() {
    }

    public static ObjectMapper getInstance() {
        if (objectMapper != null) {
            return objectMapper;
        }
        synchronized (ObjectMapper.class) {
            if (objectMapper == null) {
                objectMapper = new ObjectMapper();
                objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
                objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            }
        }
        return objectMapper;
    }
}