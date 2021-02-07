package sgb.decoder.internal;

import java.util.LinkedHashMap;
import java.util.Map;

public final class FieldsBuilder {
    
    // must preserve order of entry
    private final Map<String, Object> map = new LinkedHashMap<>();
    
    public FieldsBuilder add(String name, Object object) {
        map.put(name,  object);
        return this;
    }
    
    public Map<String, Object> build() {
        return map;
    }

}
