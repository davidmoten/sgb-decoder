package sgb.decoder.rotatingfield;

import sgb.decoder.HasFormatter;
import sgb.decoder.Indent;

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
    public String toString(Indent indent) {
        return indent.builder() //
                .right() //
                .add("Rotating field type", "Cancellation") //
                .add("Deactivation method", deactivationMethod) //
                .left() //
                .toString();
    }

}
