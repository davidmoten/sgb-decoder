package sgb.decoder.internal;

import java.util.Optional;

import com.github.davidmoten.guavamini.Preconditions;

import sgb.decoder.HasFormatter;

public final class HasFormatterHelper {

    public static String toJson(Object o) {
        Preconditions.checkNotNull(o);
        if (o instanceof HasFormatter) {
            return toJson((HasFormatter)  o);
        } else if (o instanceof Optional) {
            if (((Optional<?>) o).isEmpty()) {
                return "null";
            } else {
                return toJson(((Optional<?>) o).get());
            }
        } else if (o instanceof Number) {
            return ((Number) o).toString();
        } else if (o instanceof String) {
            return String.valueOf(o);
        } else {
            throw new RuntimeException("unexpected");
        }
    }

}
