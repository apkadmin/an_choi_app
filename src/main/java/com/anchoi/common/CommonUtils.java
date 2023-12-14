package com.anchoi.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class CommonUtils {
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str != null && str.length() > 0 && clazz != null) {
            if (clazz != Integer.TYPE && clazz != Integer.class) {
                if (clazz == String.class) {
                    return (T) str;
                } else if (clazz != Long.TYPE && clazz != Long.class) {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
                    mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

                    try {
                        return mapper.readValue(str, clazz);
                    } catch (IOException var4) {
                        return null;
                    }
                } else {
                    return (T) Long.valueOf(str);
                }
            } else {
                return (T) Integer.valueOf(str);
            }
        } else {
            return null;
        }
    }

    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        } else {
            Class<?> clazz = value.getClass();
            if (clazz != Integer.TYPE && clazz != Integer.class) {
                if (clazz == String.class) {
                    return (String)value;
                } else if (clazz != Long.TYPE && clazz != Long.class) {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    String jsonString = "";

                    try {
                        jsonString = mapper.writeValueAsString(value);
                    } catch (JsonProcessingException var5) {
                        jsonString = "Can't build json from object";
                    }

                    return jsonString;
                } else {
                    return "" + value;
                }
            } else {
                return "" + value;
            }
        }
    }

    public static toObject(Object ource, T des){
        MapStruc modelMapper = new ModelMapper();
    }
}