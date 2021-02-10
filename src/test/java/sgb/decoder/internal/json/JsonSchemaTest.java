package sgb.decoder.internal.json;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.github.davidmoten.junit.Asserts;

import sgb.decoder.Detection;
import sgb.decoder.rotatingfield.Cancellation;
import sgb.decoder.rotatingfield.EltDtInFlightEmergency;
import sgb.decoder.rotatingfield.NationalUse;
import sgb.decoder.rotatingfield.ObjectiveRequirements;
import sgb.decoder.rotatingfield.Rls;
import sgb.decoder.rotatingfield.RotatingField;
import sgb.decoder.rotatingfield.UnknownRotatingField;
import sgb.decoder.vesselid.AircraftOperatorAndSerialNumber;
import sgb.decoder.vesselid.AircraftRegistrationMarking;
import sgb.decoder.vesselid.Aviation24BitAddress;
import sgb.decoder.vesselid.Mmsi;
import sgb.decoder.vesselid.RadioCallSign;
import sgb.decoder.vesselid.VesselId;

public class JsonSchemaTest {

	@Test
	public void updateSchema() throws IOException {
		Map<Class<?>, List<Class<?>>> map = new HashMap<>();
		map.put(VesselId.class, Arrays.asList(AircraftOperatorAndSerialNumber.class, AircraftRegistrationMarking.class,
				Aviation24BitAddress.class, Mmsi.class, RadioCallSign.class));
		map.put(RotatingField.class, Arrays.asList(Cancellation.class, EltDtInFlightEmergency.class, NationalUse.class,
				ObjectiveRequirements.class, Rls.class, UnknownRotatingField.class));
		String schema = Json.prettyPrint(JsonSchema.generateSchema(Detection.class, map));
		File file = new File("src/main/json-schema/detection-schema.json");
		file.delete();
		Files.write(file.toPath(), schema.getBytes(StandardCharsets.UTF_8));
	}

	@Test
	public void testSimpleNameNoPackage() {
		assertEquals("Hello", JsonSchema.simpleName("Hello"));
	}

	@Test
	public void testSimpleNameHasPackage() {
		assertEquals("There", JsonSchema.simpleName("hello.There"));
	}

	@Test(expected = RuntimeException.class)
	public void testToClassNotFound() {
		JsonSchema.toClass("ThisClassDoesNotExist");
	}
	
	@Test
	public void isUtilityClass() {
		Asserts.assertIsUtilityClass(JsonSchema.class);
	}

}