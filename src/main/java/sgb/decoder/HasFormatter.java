package sgb.decoder;

public interface HasFormatter {

    String toString(Indent indent);

    default String toStringDefault() {
        return this.getClass().getSimpleName() + toString(new Indent(0, 2));
    }
    
    
}
