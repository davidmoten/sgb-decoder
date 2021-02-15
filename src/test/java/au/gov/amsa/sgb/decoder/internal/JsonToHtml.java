package au.gov.amsa.sgb.decoder.internal;

import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.text.StringEscapeUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;

// this class will be moved out of here soon as purely concerned with presentation
public class JsonToHtml {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String toHtml(String json) {
        try {
            JsonNode n = MAPPER.readTree(json);
            StringBuilder s = new StringBuilder();
            toHtml(n, s);
            return s.toString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static void toHtml(JsonNode node, StringBuilder s) {
        if (node instanceof ArrayNode) {
            throw new UnsupportedOperationException("Array node not supported");
        } else if (node instanceof ObjectNode) {
            ObjectNode n = (ObjectNode) node;
            if (isRange(n)) {
                s.append(escapeHtml(rangeToString(n)));
            } else {
                s.append("\n<table>");
                Iterator<Entry<String, JsonNode>> it = n.fields();
                while (it.hasNext()) {
                    Entry<String, JsonNode> entry = it.next();
                    if (entry.getKey().equalsIgnoreCase("min") || entry.getKey().equalsIgnoreCase("max")) {
                        // do nothing
                    } else {
                        s.append("\n<tr><td>" + escapeHtml(replaceTokens(camelToSpaced(entry.getKey()))) + "</td><td>");
                        toHtml(entry.getValue(), s);
                        s.append("</td></tr>");
                    }
                }
                s.append("\n</table>");
            }
        } else if (node instanceof ValueNode) {
            s.append(escapeHtml(node.asText()));
        }
    }

    private static String rangeToString(ObjectNode n) {
        JsonNode min = n.get("min");
        JsonNode max = n.get("max");
        Integer minValue = min == null ? null : min.get("value").asInt();
        Integer maxValue = max == null ? null : max.get("value").asInt();
        boolean minExclusive = min == null || min.get("exclusive") == null ? false : min.get("exclusive").asBoolean();
        boolean maxExclusive = max == null || max.get("exclusive") == null ? false : max.get("exclusive").asBoolean();
        StringBuilder s = new StringBuilder();
        if (minValue != null) {
            if (minExclusive) {
                s.append(">");
            } else {
                s.append(">=");
            }
            s.append(minValue);
        }
        if (maxValue != null) {
            if (s.length() > 0) {
                s.append(" and ");
            }
            if (maxExclusive) {
                s.append("<");
            } else {
                s.append("<=");
            }
            s.append(maxValue);
        }
        return s.toString();
    }

    private static boolean isRange(ObjectNode n) {
        Iterator<String> it = n.fieldNames();
        if (!it.hasNext()) {
            return false;
        }
        do {
            String name = it.next();
            if (!name.equalsIgnoreCase("min") && !name.equalsIgnoreCase("max")) {
                return false;
            }
        } while (it.hasNext());
        return true;
    }

    private static String replaceTokens(String s) {
        return s.replaceAll("Tac", "TAC") //
                .replaceAll("Rls", "RLS") //
                .replaceAll("Gnss", "GNSS") //
                .replaceAll("Hdop", "HDOP") //
                .replaceAll("Vdop", "VDOP") //
                .replaceAll("Lat", "Latitude") //
                .replaceAll("Lon", "Longitude");
    }

    private static String escapeHtml(String s) {
        return StringEscapeUtils.escapeHtml4(s);
    }

    private static String camelToSpaced(String s) {
        boolean startOfWord = true;
        boolean previousCharIsDigit = false;
        StringBuilder b = new StringBuilder();
        for (char ch : s.toCharArray()) {
            if (startOfWord) {
                b.append(Character.toUpperCase(ch));
                startOfWord = false;
            } else if (Character.isUpperCase(ch)) {
                b.append(" ");
                b.append(ch);
            } else if (Character.isDigit(ch)) {
                if (!previousCharIsDigit) {
                    b.append(" ");
                }
                previousCharIsDigit = true;
                b.append(ch);
            } else {
                b.append(ch);
            }
        }
        return b.toString();
    }

}
