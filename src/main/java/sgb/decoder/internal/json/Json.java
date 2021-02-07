package sgb.decoder.internal.json;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Optional;

public class Json {

    private static final char DQ = '"';
    private static final String COLON = " : ";
    private static final char COMMA = ',';

    public String generateSchema(Class<?> cls) {
        // use all private fields to generate schema
        StringBuilder s = new StringBuilder();

        return s.toString();
    }

    private String generateDefinition(Class<?> cls) {
        Arrays.stream(cls.getDeclaredFields()) //
                .map(Json::toMyField);
        return null;
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
        add(b, "type", f.javaType);
        b.append("}");
        return b.toString();
    }

    private static void add(StringBuilder b, String key, String value) {
        b.append(DQ  + key + DQ + COLON + DQ +value + DQ + COMMA);
    }

}
