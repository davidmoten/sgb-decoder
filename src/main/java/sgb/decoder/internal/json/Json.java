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

/**
 * Simplified JSON schema generator targeting {@link Detection} class and is
 * dependents.
 */
public class Json {

	private static final String LF = "\n";
	private static final char DQ = '"';
	private static final String COLON = " : ";
	private static final String COMMA = ",";

	public static String generateSchema(Class<?> cls) {
		// use all private fields to generate schema
		Map<String, Definition> clsNameDefinitions = new HashMap<>();
		collectDefinitions(cls, clsNameDefinitions);
		StringBuilder s = new StringBuilder();
		add(s, "$id", "TODO");
		s.append(COMMA + LF);
		add(s, "$schema", "TODO");
		s.append(COMMA + LF);
		s.append(properties(cls));
		return "{\n" + s.toString() + "\n}";
	}

	private static final class Definition {
		final String javaClassName;
		final String json;

		Definition(String javaClassName, String json) {
			this.javaClassName = javaClassName;
			this.json = json;
		}
	}

	private static void collectDefinitions(Class<?> cls, Map<String, Definition> clsNameDefinitions) {
		JsonType t = toJsonType(cls.getName());
		if (t.typeName.equals("object")) {
			// will be an implementation of HasFormatter
			Arrays.stream(cls.getDeclaredFields()) //
					.filter(f -> !isStatic(f)) //
					.map(Json::toMyField) //
					.peek(f -> {
						JsonType type = toJsonType(f.javaType);
						if (type.typeName.equals("object")) {
							try {
								collectDefinitions(Class.forName(f.javaType), clsNameDefinitions);
							} catch (ClassNotFoundException e) {
								throw new RuntimeException(e);
							}
						}
					});

			StringBuilder json = new StringBuilder();
			json.append(quoted(definitionName(cls)) + COLON + "{");
			add(json, "type", "object");
			json.append(properties(cls));
			// add required fields
			json.append("}");
			clsNameDefinitions.put(cls.getName(), new Definition(cls.getName(), json.toString()));
		}
	}

	private static String toRef(Class<?> cls) {
		return "#/definitions/" + cls.getSimpleName();
	}

	private static String definitionName(Class<?> cls) {
		return simpleName(cls.getName());
	}

	private static String simpleName(String javaClassName) {
		int i = javaClassName.lastIndexOf('.');
		if (i == -1) {
			return javaClassName;
		} else {
			return javaClassName.substring(i + 1);
		}
	}

	private static String properties(Class<?> cls) {
		return quoted("properties") + COLON + "{" + LF + Arrays.stream(cls.getDeclaredFields()) //
				.filter(f -> !isStatic(f)) //
				.map(Json::toMyField).map(Json::generateDefinition).collect(Collectors.joining(",\n")) + "}";
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
			b.append("[" + t.enumeration.stream().map(Json::quoted).collect(Collectors.joining(COMMA)) + "]");
			b.append(LF);
		}
		b.append("}");
		return b.toString();
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
