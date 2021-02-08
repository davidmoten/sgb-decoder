package sgb.decoder;

import sgb.decoder.internal.HasFormatterHelper;

public interface HasFormatter {
    
    default String toString(Indent indent) {
        return indent.builder().right().add(HasFormatterHelper.serializedNamesAndValues(this)).left().toString();
    }

    default String toStringDefault() {
        return this.getClass().getSimpleName() + toString(new Indent(0, 2));
    }
    
    default String toJson() {
        return HasFormatterHelper.toJson(this);
    }
}
