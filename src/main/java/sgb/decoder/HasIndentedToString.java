package sgb.decoder;

public interface HasIndentedToString {

    String toString(Indent indent);

    default String toStringDefault() {
        return this.getClass().getSimpleName() + toString(new Indent(2, 2));
    }
}
