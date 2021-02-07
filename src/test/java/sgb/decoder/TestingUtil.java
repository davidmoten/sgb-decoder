package sgb.decoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class TestingUtil {

    public static String readResource(String resourceName) {
        try (InputStream in = TestingUtil.class.getResourceAsStream(resourceName)) {
            return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
