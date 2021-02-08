package sgb.decoder.internal;

import java.lang.reflect.Field;
import java.time.OffsetTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import com.github.davidmoten.guavamini.Preconditions;

import sgb.decoder.HasFormatter;

public final class HasFormatterHelper {

	private static final char QUOTE = '"';

	public static String toJson(Object o) {
		Preconditions.checkNotNull(o);
		if (o instanceof HasFormatter) {
			return toJson((HasFormatter) o);
		} else if (o instanceof Optional) {
			if (!((Optional<?>) o).isPresent()) {
				return "null";
			} else {
				return toJson(((Optional<?>) o).get());
			}
		} else if (o instanceof Number) {
			return ((Number) o).toString();
		} else if (o instanceof Boolean) {
			return String.valueOf(o);
		} else if (o instanceof String) {
			return quoted(o);
		} else if (o instanceof OffsetTime) {
			// TODO conform to ISO8601 time
			return quoted(o);
		} else if ((o.getClass().isEnum())) {
			return quoted(o);
		} else {
			throw new RuntimeException("unexpected " + o);
		}
	}

	private static String quoted(Object o) {
		return QUOTE + String.valueOf(o) + QUOTE;
	}

	private static String toJson(HasFormatter o) {
		Fields a = o.getClass().getAnnotationsByType(Fields.class)[0];
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < a.fields().length; i++) {
			if (s.length() > 0) {
				s.append(", ");
			}
			String fieldName = a.fields()[i];
			String serializedName = a.serializedNames()[i];
			Object value;
			try {
				System.out.println(o.getClass());
				Field fld = o.getClass().getDeclaredField(fieldName);
				fld.setAccessible(true);
				value = fld.get(o);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				throw new RuntimeException(e);
			}
			if (isPresent(value)) {
				s.append(quoted(serializedName) + ":" + toJson(value));
			}
		}
		return "{" + s + "}";
	}

	private static boolean isPresent(Object o) {
		if (o instanceof Optional) {
			return ((Optional<?>) o).isPresent();
		} else {
			return o != null;
		}
	}

	public static Map<String, Object> serializedNamesAndValues(HasFormatter o) {
		Map<String, Object> map = new LinkedHashMap<>();
		Fields a = o.getClass().getAnnotationsByType(Fields.class)[0];
		for (int i = 0; i < a.fields().length; i++) {
			String fieldName = a.fields()[i];
			String serializedName = a.serializedNames()[i];
			final Object value;
			try {
				Field fld = o.getClass().getDeclaredField(fieldName);
				fld.setAccessible(true);
				value = fld.get(o);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				throw new RuntimeException(e);
			}
			if (isPresent(value)) {
				map.put(serializedName, value);
			}
		}
		return map;
	}

}
