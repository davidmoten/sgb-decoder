package sgb.decoder.rotatingfield;

import java.util.Map;

import sgb.decoder.HasFormatter;
import sgb.decoder.internal.Util;

public final class Cancellation implements RotatingField, HasFormatter {

    private final DeactivationMethod deactivationMethod;

    public Cancellation(DeactivationMethod deactivationMethod) {
        this.deactivationMethod = deactivationMethod;
    }

    public DeactivationMethod deactivationMethod() {
        return deactivationMethod;
    }

    @Override
    public String toString() {
        return toStringDefault();
    }

    @Override
    public Map<String, Object> fields() {
        return Util.fieldsBuilder() //
                .add("rotatingFieldType", "Cancellation") //
                .add("deactivationMethod", deactivationMethod) //
                .build();
    }

}
