package au.gov.amsa.sgb.decoder.internal.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import au.gov.amsa.sgb.decoder.rotatingfield.Range;
import au.gov.amsa.sgb.decoder.rotatingfield.RangeEndType;

public final class RangeSerializer extends StdSerializer<Range> {

    public RangeSerializer() {
        super((Class<Range>) null);
    }

    private static final long serialVersionUID = -503194162259684892L;

    @Override
    public void serialize(Range range, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartObject();
        if (range.minType() != RangeEndType.MISSING) {
            gen.writeNumberField("min", range.min());
            gen.writeBooleanField("minInclusive", range.minType() == RangeEndType.INCLUSIVE);
        }
        if (range.maxType() != RangeEndType.MISSING) {
            gen.writeNumberField("max", range.max());
            gen.writeBooleanField("maxInclusive", range.maxType() == RangeEndType.INCLUSIVE);
        }
        gen.writeEndObject();
    }

}
