package au.gov.amsa.sgb.decoder;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.io.Files;

public class ComplianceKitTest {

    @Test
    public void test() throws IOException {
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
                TestingUtil.assertJsonEquals(json, d.toJson());
            }
        }
    }
    private static String removeQuotes(String s) {
        s = s.trim();
        return s.substring(1, s.length() - 1);
    }

}
