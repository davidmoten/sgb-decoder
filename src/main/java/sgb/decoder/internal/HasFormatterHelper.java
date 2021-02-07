package sgb.decoder.internal;

import java.time.OffsetTime;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.davidmoten.guavamini.Preconditions;

import sgb.decoder.HasFormatter;

public final class HasFormatterHelper {

    private static final char QUOTE = '"';

    public static String toJson(Object o) {
        Preconditions.checkNotNull(o);
        if (o instanceof HasFormatter) {
            return toJson((HasFormatter) o);
        } else if (o instanceof Optional) {
            if (((Optional<?>) o).isEmpty()) {
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
        return "{" + o.fields().entrySet().stream() //
                .filter(entry -> isPresent(entry.getValue()))
                .map(entry -> QUOTE + entry.getKey() + QUOTE + " : " + toJson(entry.getValue())) //
                .collect(Collectors.joining(", ")) + "}";
    }

    private static boolean isPresent(Object o) {
        if (o instanceof Optional) {
            return ((Optional<?>) o).isPresent();
        } else {
            return o != null;
        }
    }

}
