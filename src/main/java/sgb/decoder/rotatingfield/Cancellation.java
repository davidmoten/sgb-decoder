package sgb.decoder.rotatingfield;

public final class Cancellation implements RotatingField {

    private final DeactivationMethod deactivationMethod;

    public Cancellation(DeactivationMethod deactivationMethod) {
        this.deactivationMethod = deactivationMethod;
    }
    
    public DeactivationMethod deactivationMethod() {
        return deactivationMethod;
    }

}
