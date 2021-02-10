package sgb.decoder.internal;

import java.util.HashMap;
import java.util.Map;

public final class Hex {

    private Hex() {
        // prevent instantiation
    }

    private static Map<String, String> map = createMap();

    private static Map<String, String> createMap() {
        Map<String, String> m = new HashMap<>();
        m.put("0", "0000");
        m.put("1", "0001");
        m.put("2", "0010");
        m.put("3", "0011");
        m.put("4", "0100");
        m.put("5", "0101");
        m.put("6", "0110");
        m.put("7", "0111");
        m.put("8", "1000");
        m.put("9", "1001");
        m.put("A", "1010");
        m.put("B", "1011");
        m.put("C", "1100");
        m.put("D", "1101");
        m.put("E", "1110");
        m.put("F", "1111");
        return m;
    }

    public static String hexToBin(String s) {
        char[] hex = s.toCharArray();
        StringBuilder b = new StringBuilder();
        for (char h : hex) {
            b.append(map.get(String.valueOf(h)));
        }
        return b.toString();
    }
}
