package sgb.decoder.rotatingfield;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import sgb.decoder.Indent;

public final class RangeTest {

    @Test
    public void testRange() {
        Range r = Range.create(1, RangeEndType.EXCLUSIVE, 6, RangeEndType.INCLUSIVE);
        assertEquals(1, r.min());
        assertEquals(RangeEndType.EXCLUSIVE, r.minType());
        assertEquals(6, r.max());
        assertEquals(RangeEndType.INCLUSIVE, r.maxType());
        assertEquals("Range [min=1, minType=EXCLUSIVE, max=6, maxType=INCLUSIVE]",
                r.toString());
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
        assertFalse(r.equals(123));
        assertFalse(r.equals(Range.create(2, RangeEndType.EXCLUSIVE, 6, RangeEndType.INCLUSIVE)));
        assertFalse(r.equals(Range.create(1, RangeEndType.INCLUSIVE, 6, RangeEndType.INCLUSIVE)));
        assertFalse(r.equals(Range.create(1, RangeEndType.EXCLUSIVE, 7, RangeEndType.INCLUSIVE)));
        assertFalse(r.equals(Range.create(1, RangeEndType.EXCLUSIVE, 6, RangeEndType.EXCLUSIVE)));
        assertTrue(r.equals(Range.create(1, RangeEndType.EXCLUSIVE, 6, RangeEndType.INCLUSIVE)));
    }

    @Test
    public void testRangeToStringIndent() {
        Indent indent = new Indent(0, 2);
        {
            Range r = Range.create(1, RangeEndType.INCLUSIVE, 2, RangeEndType.INCLUSIVE);
            assertEquals(">=1 and <=2", r.toString(indent));
        }
        {
            Range r = Range.create(1, RangeEndType.EXCLUSIVE, 2, RangeEndType.INCLUSIVE);
            assertEquals(">1 and <=2", r.toString(indent));
        }
        {
            Range r = Range.create(1, RangeEndType.MISSING, 2, RangeEndType.INCLUSIVE);
            assertEquals("<=2", r.toString(indent));
        }
        {
            Range r = Range.create(1, RangeEndType.INCLUSIVE, 2, RangeEndType.EXCLUSIVE);
            assertEquals(">=1 and <2", r.toString(indent));
        }
        {
            Range r = Range.create(1, RangeEndType.INCLUSIVE, 2, RangeEndType.MISSING);
            assertEquals(">=1", r.toString(indent));
        }
        {
            Range r = Range.create(1, RangeEndType.INCLUSIVE, 2, RangeEndType.INCLUSIVE);
            assertEquals(">=1 and <=2", r.toString(indent));
        }
    }

}
