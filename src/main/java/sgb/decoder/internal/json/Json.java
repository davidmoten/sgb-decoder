package sgb.decoder.internal.json;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import sgb.decoder.rotatingfield.Range;

public final class Json {

	private Json() {
		// prevent instantiation
	}

	public static String toJson(Object o) {
		ObjectMapper m = createMapper();
		try {
			return m.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private static ObjectMapper createMapper() {
		ObjectMapper m = new ObjectMapper();

		// Avoid having to annotate the Person class
		// Requires Java 8, pass -parameters to javac
		// and jackson-module-parameter-names as a dependency
		m.registerModule(new ParameterNamesModule(PROPERTIES));
		m.registerModule(new Jdk8Module());

		SimpleModule custom = new SimpleModule();
		custom.addSerializer(Range.class, new RangeSerializer());
		m.registerModule(custom);

		// make private fields of Person visible to Jackson
		m.setVisibility(FIELD, ANY);
		m.setSerializationInclusion(Include.NON_NULL);
		return m;
	}

	public static boolean equals(String json1, String json2) {
		ObjectMapper m = new ObjectMapper();
		try {
			return m.readTree(json1).equals(m.readTree(json2));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
