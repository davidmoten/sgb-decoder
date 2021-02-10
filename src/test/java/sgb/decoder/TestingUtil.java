package sgb.decoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.junit.ComparisonFailure;

import sgb.decoder.internal.json.Json;

public final class TestingUtil {

	public static String readResource(String resourceName) {
		try (InputStream in = TestingUtil.class.getResourceAsStream(resourceName)) {
			return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)).lines()
					.collect(Collectors.joining("\n"));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static void assertResourceEqualsJson(String resourceName, String json) {
		String expected = readResource(resourceName);
		if (!Json.equals(expected, json)) {
			throw new ComparisonFailure("unequal json", expected, prettyPrintJson(json));
		}
	}

	/**
	 * A simple implementation to pretty-print JSON file.
	 *
	 * @param json
	 * @return
	 */
	public static String prettyPrintJson(String json) {
		StringBuilder b = new StringBuilder();
		int indentLevel = 0;
		boolean inQuote = false;
		for (char ch : json.toCharArray()) {
			switch (ch) {
			case '"':
				// switch the quoting status
				inQuote = !inQuote;
				b.append(ch);
				break;
			case ' ':
				// For space: ignore the space if it is not being quoted.
				if (inQuote) {
					b.append(ch);
				}
				break;
			case '{':
			case '[':
				// Starting a new block: increase the indent level
				b.append(ch);
				indentLevel++;
				appendIndentedNewLine(indentLevel, b);
				break;
			case '}':
			case ']':
				// Ending a new block; decrese the indent level
				indentLevel--;
				appendIndentedNewLine(indentLevel, b);
				b.append(ch);
				break;
			case ',':
				// Ending a json item; create a new line after
				b.append(ch);
				if (!inQuote) {
					appendIndentedNewLine(indentLevel, b);
				}
				break;
			case ':':
				if (!inQuote) {
					// key value delimiter
					b.append(" : ");
				} else {
					b.append(ch);
				}
				break;
			default:
				b.append(ch);
			}
		}
		return b.toString();
	}

	/**
	 * Print a new line with indention at the beginning of the new line.
	 * 
	 * @param indentLevel
	 * @param stringBuilder
	 */
	private static void appendIndentedNewLine(int indentLevel, StringBuilder stringBuilder) {
		stringBuilder.append("\n");
		for (int i = 0; i < indentLevel; i++) {
			// Assuming indention using 2 spaces
			stringBuilder.append("    ");
		}
	}

}
