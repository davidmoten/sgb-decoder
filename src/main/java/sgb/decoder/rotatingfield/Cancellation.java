package sgb.decoder.rotatingfield;

import sgb.decoder.HasIndentedToString;
import sgb.decoder.Indent;

public final class Cancellation implements RotatingField, HasIndentedToString {

    private final DeactivationMethod deactivationMethod;

    public Cancellation(DeactivationMethod deactivationMethod) {
        this.deactivationMethod = deactivationMethod;
    }

    public DeactivationMethod deactivationMethod() {
        return deactivationMethod;
    }

    @Override
    public String toString(Indent indent) {
        return indent.builder() //
                .left() //
                .add("Rotating field type", "Cancellation") //
                .add("Deactivation method", deactivationMethod) //
                .right() //
                .toString();
    }

}
