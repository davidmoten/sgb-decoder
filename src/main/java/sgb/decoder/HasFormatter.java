package sgb.decoder;

import java.util.Map;

public interface HasFormatter {
    
    Map<String, Object> fields();

    default String toString(Indent indent) {
        return indent.builder().right().add(fields()).left().toString();
    }

    default String toStringDefault() {
        return this.getClass().getSimpleName() + toString(new Indent(0, 2));
    }
    
    
}
