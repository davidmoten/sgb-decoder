package au.gov.amsa.sgb.decoder;

import static au.gov.amsa.sgb.decoder.TestingUtil.assertJsonEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.io.Files;

import au.gov.amsa.sgb.decoder.internal.json.Json;

public class ComplianceKitTest {

    @Test
    public void runAllComplianceTests() throws IOException {
        String base = "src/test/resources/compliance-kit";
        Charset charset = StandardCharsets.UTF_8;
        List<String> lines = Files.readLines(new File(base, "tests.csv"), charset);
        for (String line : lines.subList(1, lines.size())) {
            System.out.println(line);
            String[] items = line.split(",");
            assertEquals(4, items.length);
            String type = removeQuotes(items[0]);
            String hex = removeQuotes(items[2]);
            String filename = removeQuotes(items[3]);
            String json = Files.readLines(new File(base, filename), charset) //
                    .stream() //
                    .collect(Collectors.joining("\n"));
            if (type.trim().equalsIgnoreCase("Detection")) {
                Detection d = Detection.fromHexGroundSegmentRepresentation(hex);
                assertJsonEquals(json, d.toJson());
            } else {
                Beacon23HexId b = Beacon23HexId.fromHex(hex);
                assertJsonEquals(json, b.toJson());
            }
        }
    }

    @Test
    public void testCreateComplianceKit() {
        // saves the compliance kit to target for copying to src/test/resources when
        // happy

        File kit = new File("target/compliance-kit");
        kit.mkdirs();
        Arrays.stream(kit.listFiles()).forEach(f -> f.delete());

        List<KitTest> tests = new ArrayList<>();
        KitTest.type(TestType.DETECTION) //
                .title("Specification example B-1 no Vessel Id") //
                .hex("0039823D32618658622811F0000000000003FFF004030680258") //
                .filename("detection-specification-example.json") //
                .addTo(kit, tests);
        KitTest.type(TestType.DETECTION) //
                .title("Example B-2 with Vessel Id of type MMSI") //
                .hex("0039823D32618658622811F23ADE68AA17E3FFF004030680258") //
                .filename("detection-with-mmsi-vessel-id.json") //
                .addTo(kit, tests);
        KitTest.type(TestType.DETECTION) //
                .title("Example B-2 with Vessel Id Aircraft Operator and Serial Number") //
                .hex("0039823D32618658622811FB7AC403C00003FFF004030680258") //
                .filename("detection-with-aircraft-operator-and-serial-number.json") //
                .addTo(kit, tests);
        KitTest.type(TestType.DETECTION) //
                .title("Example B-2 with Vessel Id Aircraft Registration Marking") //
                .hex("0039823D32618658622811F6497CAC719DC3FFF004030680258") //
                .filename("detection-with-aircraft-registration-marking.json") //
                .addTo(kit, tests);
        KitTest.type(TestType.BEACON_23_HEX_ID) //
                .title("Specification example B-2") //
                .hex("9934039823D000000000000") //
                .filename("beacon-23-hex-id-sample.json") //
                .addTo(kit, tests);

        writeCsv(kit, tests);
    }

    private static void writeCsv(File kit, List<KitTest> tests) {
        // TODO Auto-generated method stub

    }

    private static void write(File kit, KitTest t) {
        final String json;
        if (t.type == TestType.DETECTION) {
            Detection d = Detection.fromHexGroundSegmentRepresentation(t.hex);
            json = Json.prettyPrint(d.toJson());
        } else {
            Beacon23HexId b = Beacon23HexId.fromHex(t.hex);
            json = Json.prettyPrint(b.toJson());
        }
        try {
            Files.write(json.getBytes(StandardCharsets.UTF_8), new File(kit, t.filename));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private enum TestType {
        DETECTION("Detection"), BEACON_23_HEX_ID("Beacon 23 Hex Id");

        private final String name;

        TestType(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    private static final class KitTest {
        final TestType type;
        final String title;
        final String hex;
        final String filename;

        private KitTest(TestType type, String title, String hex, String filename) {
            this.type = type;
            this.title = title;
            this.hex = hex;
            this.filename = filename;
        }

        public void addTo(File kit, List<KitTest> tests) {
            tests.add(this);
            write(kit, this);
        }

        public static Builder type(TestType type) {
            return new Builder(type);
        }

        public static class Builder {
            private TestType type;
            private String title;
            private String hex;

            Builder(TestType type) {
                this.type = type;
            }

            public Builder title(String title) {
                this.title = title;
                return this;
            }

            public Builder hex(String hex) {
                this.hex = hex;
                return this;
            }

            public KitTest filename(String filename) {
                return new KitTest(type, title, hex, filename);
            }
        }
    }

    private static String removeQuotes(String s) {
        s = s.trim();
        return s.substring(1, s.length() - 1);
    }

}
