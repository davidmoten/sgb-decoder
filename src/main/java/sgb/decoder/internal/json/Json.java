package sgb.decoder.internal.json;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import sgb.decoder.Detection;

public class Json {

    private static final String LF = "\n";
    private static final char DQ = '"';
    private static final String COLON = " : ";
    private static final char COMMA = ',';

    public static String generateSchema(Class<?> cls) {
        // use all private fields to generate schema
        StringBuilder s = new StringBuilder();
        s.append(properties(cls));
        return s.toString();
    }

    private static String properties(Class<?> cls) {
        return "{" + quoted("properties") + COLON + "{" + LF
                + Arrays.stream(cls.getDeclaredFields()) //
                        .filter(f -> !isStatic(f)) //
                        .map(Json::toMyField).map(Json::generateDefinition)
                        .collect(Collectors.joining(",\n"))
                + "}}";
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

    private static String generateDefinition(MyField f) {
        StringBuilder b = new StringBuilder();
        b.append(DQ);
        b.append(f.name);
        b.append(DQ);
        b.append(" : ");
        b.append("{");
        JsonType t = toJsonType(f.javaType);
        add(b, "type", t.typeName);
        if (!t.enumeration.isEmpty()) {
            b.append("," + LF);
            b.append(quoted("enum") + COLON);
            b.append("[" + t.enumeration.stream().map(Json::quoted).collect(Collectors.joining(","))
                    + "]");
            b.append(LF);
        }
        b.append("}");
        return b.toString();
    }

    private static void add(StringBuilder b, String key, String value) {
        b.append(quoted(key) + COLON + quoted(value));
    }

    private static String quoted(String s) {
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
            Class<?> cls;
            try {
                cls = Class.forName(javaType);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
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
        return map;
    }

    public static void main(String[] args) {
        System.out.println(Json.generateSchema(Detection.class));
    }

}
