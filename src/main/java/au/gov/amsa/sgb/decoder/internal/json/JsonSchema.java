package au.gov.amsa.sgb.decoder.internal.json;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.time.OffsetTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.davidmoten.guavamini.annotations.VisibleForTesting;

import au.gov.amsa.sgb.decoder.Detection;

/**
 * Simplified JSON schema generator targeting {@link Detection} class and is
 * dependents.
 */
public final class JsonSchema {

    private static final char DQ = '"';
    private static final String COLON = " : ";
    private static final String COMMA = ",";

    private JsonSchema() {
        // prevent instantiation
    }

    public static String generateSchema(Class<?> cls, Map<Class<?>, List<Class<?>>> subclasses) {
        // use all private fields to generate schema
        Map<String, Definition> clsNameDefinitions = new HashMap<>();
        collectDefinitions(cls, clsNameDefinitions, subclasses);
        StringBuilder s = new StringBuilder();
        add(s, "$id", "https://amsa.gov.au/sgb");
        s.append(COMMA);
        add(s, "$schema", "http://json-schema.org/draft/2019-09/schema");
        s.append(COMMA);
        s.append(quoted("definitions") + COLON + "{");
        s.append(clsNameDefinitions.values() //
                .stream() //
                .map(x -> x.json) //
                .collect(Collectors.joining(",")));
        s.append("}");
        return "{" + s.toString() + "}";
    }

    private static final class Definition {
        final String json;

        Definition(String json) {
            this.json = json;
        }
    }

    private static void collectDefinitions(Class<?> cls, Map<String, Definition> clsNameDefinitions,
            Map<Class<?>, List<Class<?>>> subclasses) {
        collectDefinitions(cls, clsNameDefinitions, new HashSet<String>(), subclasses);
    }

    private static void collectDefinitions(Class<?> cls, Map<String, Definition> clsNameDefinitions,
            Set<String> classesAlreadyProcessed, Map<Class<?>, List<Class<?>>> subclasses) {
        JsonType t = toJsonType(cls.getName());
        if (t.typeName.equals("object") && !classesAlreadyProcessed.contains(cls.getName())) {
            classesAlreadyProcessed.add(cls.getName());
            // will be an implementation of HasFormatter
            Arrays.stream(cls.getDeclaredFields()) //
                    .filter(f -> !isStatic(f)) //
                    .filter(f -> !ignore(f)) //
                    .map(JsonSchema::toMyField) //
                    .forEach(f -> {
                        JsonType type = toJsonType(f.javaType);
                        if (type.typeName.equals("object")) {
                            collectDefinitions(toClass(f.javaType), clsNameDefinitions,
                                    classesAlreadyProcessed, subclasses);
                        } else if (type.typeName.equals("string") && !type.enumeration.isEmpty()) {
                            StringBuilder json = new StringBuilder();
                            json.append(quoted(definitionName(f.javaType)) + COLON + "{");
                            add(json, "type", "string");
                            json.append(", ");
                            json.append(quoted("enum") + COLON);
                            json.append("[" + type.enumeration.stream().map(JsonSchema::quoted)
                                    .collect(Collectors.joining(COMMA)) + "]");
                            json.append("}");
                            clsNameDefinitions.put(type.typeName, new Definition(json.toString()));
                        }
                    });
            StringBuilder json = new StringBuilder();
            json.append(quoted(definitionName(cls)) + COLON + "{");
            List<Class<?>> list = subclasses.get(cls);
            if (list != null) {
                StringBuilder s = new StringBuilder();
                // cls must be an interface because we don't use class inheritance
                for (Class<?> c : list) {
                    collectDefinitions(c, clsNameDefinitions, subclasses);
                    if (s.length() > 0) {
                        s.append(", ");
                    }
                    s.append("{" + quoted("$ref") + COLON + quoted(toRef(c)) + "}");

                }
                String types = "[" + s.toString() + "]";
                json.append(quoted("oneOf") + COLON + types);
            } else {
                json.append(quoted("type") + COLON + quoted("object"));
            }

            String properties = properties(cls);
            if (!properties.isEmpty()) {
                json.append(", ");
                json.append(properties);
            }

            String required = Arrays.stream(cls.getDeclaredFields()) //
                    .filter(f -> !isStatic(f)) //
                    .map(JsonSchema::toMyField) //
                    .filter(f -> f.required) //
                    .map(f -> quoted(f.name)) //
                    .collect(Collectors.joining(", "));

            if (!required.isEmpty()) {
                json.append(COMMA);
                json.append(quoted("required") + COLON + "[" + required + "]");
            }
            json.append("}");
            clsNameDefinitions.put(cls.getName(), new Definition(json.toString()));
        }
    }

    private static boolean ignore(Field f) {
        JsonIgnore a = f.getAnnotation(JsonIgnore.class);
        return a != null;
    }

    @VisibleForTesting
    static Class<?> toClass(String javaClassName) {
        try {
            return Class.forName(javaClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateDefinition(MyField f) {
        StringBuilder b = new StringBuilder();
        b.append(DQ);
        b.append(f.name);
        b.append(DQ);
        b.append(" : ");
        b.append("{");
        JsonType t = toJsonType(f.javaType);
        if (t.typeName.equals("object")) {
            add(b, "$ref", toRef(f.javaType));
        } else if (t.typeName.equals("time")) {
            add(b, "type", "string");
            b.append(COMMA);
            add(b, "format", "time");
        } else {
            add(b, "type", t.typeName);
        }

        b.append("}");
        return b.toString();
    }

    private static String toRef(Class<?> cls) {
        return "#/definitions/" + definitionName(cls);
    }

    private static String toRef(String javaClassName) {
        return "#/definitions/" + definitionName(javaClassName);
    }

    private static String definitionName(Class<?> cls) {
        return definitionName(cls.getName());
    }

    private static String definitionName(String javaClassName) {
        return simpleName(javaClassName);
    }

    @VisibleForTesting
    static String simpleName(String javaClassName) {
        int i = javaClassName.lastIndexOf('.');
        if (i == -1) {
            return javaClassName;
        } else {
            return javaClassName.substring(i + 1);
        }
    }

    private static String properties(Class<?> cls) {
        String content = Arrays.stream(cls.getDeclaredFields()) //
                .filter(f -> !isStatic(f)) //
                .map(JsonSchema::toMyField) //
                .map(JsonSchema::generateDefinition) //
                .collect(Collectors.joining(","));
        if (content.trim().isEmpty()) {
            return "";
        } else {
            return quoted("properties") + COLON + "{" + content //
                    + "}";
        }
    }

    private static boolean isStatic(Field f) {
        return Modifier.isStatic(f.getModifiers());
    }

    private static MyField toMyField(Field field) {
        Class<?> t = field.getType();
        final boolean required;
        final String javaType;
        if (t.equals(Optional.class)) {
            ParameterizedType p = (ParameterizedType) field.getGenericType();
            javaType = p.getActualTypeArguments()[0].getTypeName();
            required = false;
        } else {
            javaType = t.getName();
            required = true;
        }
        return new MyField(field.getName(), javaType, required);
    }

    private static void add(StringBuilder b, String key, String value) {
        b.append(quoted(key) + COLON + quoted(value));
    }

    public static String quoted(String s) {
        return DQ + s + DQ;
    }

    private static final Map<String, String> javaTypeToJsonType = createJavaTypeToJsonTypeMap();

    private static final class JsonType {
        final String typeName;
        final List<String> enumeration;

        private JsonType(String name, List<String> enumeration) {
            this.typeName = name;
            this.enumeration = enumeration;
        }
    }

    private static JsonType toJsonType(String javaType) {
        String t = javaTypeToJsonType.get(javaType);
        if (t != null) {
            return new JsonType(t, Collections.emptyList());
        } else {
            Class<?> cls = toClass(javaType);
            if (cls.isEnum()) {
                List<String> list = Arrays.stream(cls.getEnumConstants()).map(x -> x.toString())
                        .collect(Collectors.toList());
                return new JsonType("string", list);
            } else {
                return new JsonType("object", Collections.emptyList());
            }
        }
    }

    private static Map<String, String> createJavaTypeToJsonTypeMap() {
        Map<String, String> map = new HashMap<>();
        map.put(Boolean.class.getName(), "boolean");
        map.put("boolean", "boolean");
        map.put(Integer.class.getName(), "integer");
        map.put("int", "integer");
        map.put(Double.class.getName(), "number");
        map.put("double", "number");
        map.put(String.class.getName(), "string");
        map.put(OffsetTime.class.getName(), "time");
        return map;
    }

}