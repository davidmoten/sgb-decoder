package sgb.decoder.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Fields {

	/**
	 * All field names in the declaring class that are to be included in
	 * serialization.
	 * 
	 * @return field names to be included in the serialization
	 */
	String[] fields();

	/**
	 * The serialized name of each field in the order of {@link Fields#fields()}.
	 * This array should have the same length as {@code fields()}.
	 * 
	 * @return serialized names
	 */
	String[] serializedNames();

}
