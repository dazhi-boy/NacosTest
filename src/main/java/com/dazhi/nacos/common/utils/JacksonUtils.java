package com.dazhi.nacos.common.utils;

import com.dazhi.nacos.api.exception.runtime.NacosDeserializationException;
import com.dazhi.nacos.api.exception.runtime.NacosSerializationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JacksonUtils {
    static ObjectMapper mapper = new ObjectMapper();

    public static <T> T toObj(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            throw new NacosDeserializationException(typeReference.getClass(), e);
        }
    }

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new NacosSerializationException(obj.getClass(), e);
        }
    }
}
