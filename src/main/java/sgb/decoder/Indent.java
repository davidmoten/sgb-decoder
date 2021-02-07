package sgb.decoder;

import java.util.Map;
import java.util.Optional;

import com.github.davidmoten.guavamini.Preconditions;

import sgb.decoder.internal.Util;

public final class Indent {

    private int length = 0;
    private int step;

    public Indent(int length, int step) {
        this.length = length;
        this.step = step;
        checkLength();
    }

    private void checkLength() {
        Preconditions.checkArgument(length >= 0, "left has been called more times than right");
    }

    public Indent left() {
        length -= step;
        checkLength();
        return this;
    }

    public Indent right() {
        length += step;
        checkLength();
        return this;
    }

    public String of(Object o) {
        if (o instanceof Optional) {
            return ((Optional<?>) o).map(x -> of(x)).orElse("");
        } else if (o instanceof HasFormatter) {
            return ((HasFormatter) o).toString(this);
        } else {
            return String.valueOf(o);
        }
    }

    public IndentBuilder builder() {
        return new IndentBuilder(this, new StringBuilder());
    }

    public static final class IndentBuilder {

        private final StringBuilder b;
        private final Indent indent;

        IndentBuilder(Indent indent, StringBuilder b) {
            this.indent = indent;
            this.b = b;
        }

        public IndentBuilder add(Map<String, Object> fields) {
            fields.entrySet().stream()
                    .forEach(entry -> IndentBuilder.this.add(entry.getKey(), entry.getValue()));
            return this;
        }

        public IndentBuilder add(String name, Object value) {
            b.append("\n");
            b.append(indent);
            b.append(name);
            b.append(" = ");
            b.append(indent.of(value));
            return this;
        }

        public IndentBuilder left() {
            indent.left();
            return this;
        }

        public IndentBuilder right() {
            indent.right();
            return this;
        }

        @Override
        public String toString() {
            return b.toString();
        }

    }

    public String toString() {
        return Util.repeat(' ', length);
    }
}
