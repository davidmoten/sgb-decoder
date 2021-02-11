package au.gov.amsa.sgb.decoder.rotatingfield;

import static au.gov.amsa.sgb.decoder.TestingUtil.assertJsonEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import au.gov.amsa.sgb.decoder.internal.json.Json;
import au.gov.amsa.sgb.decoder.rotatingfield.Range;
import au.gov.amsa.sgb.decoder.rotatingfield.RangeEndType;

public final class RangeTest {

    @Test
    public void testRange() {
        Range r = Range.create(1, RangeEndType.EXCLUSIVE, 6, RangeEndType.INCLUSIVE);
        assertEquals(1, r.min());
        assertEquals(RangeEndType.EXCLUSIVE, r.minType());
        assertEquals(6, r.max());
        assertEquals(RangeEndType.INCLUSIVE, r.maxType());
        assertEquals("Range [min=1, minType=EXCLUSIVE, max=6, maxType=INCLUSIVE]", r.toString());
        // get coverage
        r.hashCode();
    }

    @Test
    public void testRangeMissing() {
        Range r = Range.create(5, RangeEndType.MISSING, 7, RangeEndType.MISSING);
        assertEquals(0, r.min());
        assertEquals(0, r.max());
    }

    @Test
    public void testEquals() {
        Range r = Range.create(1, RangeEndType.EXCLUSIVE, 6, RangeEndType.INCLUSIVE);
        assertFalse(r.equals(null));
        assertTrue(r.equals(r));
        assertFalse(r.equals(new Object()));
        assertFalse(r.equals(Range.create(2, RangeEndType.EXCLUSIVE, 6, RangeEndType.INCLUSIVE)));
        assertFalse(r.equals(Range.create(1, RangeEndType.INCLUSIVE, 6, RangeEndType.INCLUSIVE)));
        assertFalse(r.equals(Range.create(1, RangeEndType.EXCLUSIVE, 7, RangeEndType.INCLUSIVE)));
        assertFalse(r.equals(Range.create(1, RangeEndType.EXCLUSIVE, 6, RangeEndType.EXCLUSIVE)));
        assertTrue(r.equals(Range.create(1, RangeEndType.EXCLUSIVE, 6, RangeEndType.INCLUSIVE)));
    }

    @Test
    public void testRangeToJson() {
        {
            Range r = Range.create(1, RangeEndType.INCLUSIVE, 2, RangeEndType.INCLUSIVE);
            assertJsonEquals("{\"min\" : 1, \"minInclusive\" : true, \"max\" : 2, \"maxInclusive\" : true}",
                    Json.toJson(r));
        }
        {
            Range r = Range.create(1, RangeEndType.EXCLUSIVE, 2, RangeEndType.INCLUSIVE);
            assertJsonEquals("{\"min\" : 1, \"minInclusive\" : false, \"max\" : 2, \"maxInclusive\" : true}",
                    Json.toJson(r));
        }
        {
            Range r = Range.create(1, RangeEndType.MISSING, 2, RangeEndType.INCLUSIVE);
            assertJsonEquals("{\"max\" : 2, \"maxInclusive\" : true}", Json.toJson(r));
        }
        {
            Range r = Range.create(1, RangeEndType.INCLUSIVE, 2, RangeEndType.EXCLUSIVE);
            assertJsonEquals("{\"min\" : 1, \"minInclusive\" : true, \"max\" : 2, \"maxInclusive\" : false}",
                    Json.toJson(r));
        }
        {
            Range r = Range.create(1, RangeEndType.INCLUSIVE, 2, RangeEndType.MISSING);
            assertJsonEquals("{\"min\" : 1, \"minInclusive\" : true}", Json.toJson(r));
        }
        {
            Range r = Range.create(1, RangeEndType.INCLUSIVE, 2, RangeEndType.INCLUSIVE);
            assertJsonEquals("{\"min\" : 1, \"minInclusive\" : true, \"max\" : 2, \"maxInclusive\" : true}",
                    Json.toJson(r));
        }
    }

}