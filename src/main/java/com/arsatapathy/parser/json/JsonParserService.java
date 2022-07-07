package com.arsatapathy.parser.json;

import com.arsatapathy.parser.json.annocation.JsonElement;
import com.arsatapathy.parser.json.annocation.JsonInitializer;
import com.arsatapathy.parser.json.annocation.JsonParser;
import com.arsatapathy.parser.json.exception.JsonParserException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class JsonParserService {

    private final Logger LOG = Logger.getLogger(JsonParserService.class.getSimpleName());

    private void validator(Object object) throws JsonParserException {
        if (Objects.isNull(object)) {
            throw new JsonParserException("Object to be parsed can't be null!");
        }

        Class<?> clazz = object.getClass();

        if (!clazz.isAnnotationPresent(JsonParser.class)) {
            throw new JsonParserException(clazz.getSimpleName() + " is not annotated with @JsonParser");
        }
    }

    private void initializer(Object object) throws Exception {
        Class<?> clazz = object.getClass();

        for (Method method :clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(JsonInitializer.class)) {
                LOG.info("initializer method with @JsonInitializer found: " + method.getName());
                method.setAccessible(true);
                method.invoke(object);
            }
        }
    }

    private Map<String, String> elementScanner(Object object) throws Exception {
        Map<String, String> elements = new LinkedHashMap<>();

        Class<?> clazz = object.getClass();

        for (Field field :clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(JsonElement.class)) {
                LOG.info("field with @JsonElement found: " + field.getName());
                field.setAccessible(true);
                elements.put(getKey(field), (String) field.get(object));
            }
        }

        return elements;
    }

    private String getKey(Field field) {
        String key = field.getAnnotation(JsonElement.class).key();

        return key.isEmpty() ? field.getName() : key;
    }

    public String getJson(Object object) throws JsonParserException {
        try {
            validator(object);
            initializer(object);

            String jsonString = elementScanner(object).entrySet()
                    .stream()
                    .map(element -> "\"" + element.getKey() + "\":\"" + element.getValue() + "\"")
                    .collect(Collectors.joining(","));

            jsonString = "{" + jsonString + "}";

            LOG.info("Json parsing successful:" + jsonString);

            return jsonString;

        } catch (Exception e) {
            throw new JsonParserException(e.getMessage());
        }
    }
}
