package sgb.decoder.internal;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class BitsTest {

    @Test
    public void testReadUnsignedInt() {
        Bits b = Bits.from("10110001");
        assertEquals(5, b.readUnsignedInt(3));
        assertEquals(17, b.readUnsignedInt(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromBadBits() {
        Bits.from("1001A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadPositionNegative() {
        Bits.from("111").position(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadPositionTooHigh() {
        Bits.from("111").position(4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSkipNegative() {
        Bits.from("111").skip(-1);
    }

    @Test
    public void testEof() {
        Bits b = Bits.from("111");
        assertFalse(b.atEnd());
        b.skip(1);
        assertFalse(b.atEnd());
        b.skip(2);
        assertTrue(b.atEnd());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadIntZeroLength() {
        Bits.from("111").readUnsignedInt(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadIntPastEnd() {
        Bits.from("111").readUnsignedInt(5);
    }

    @Test
    public void testReadBaudotShort() {
        Bits b = Bits.from("1100010011");
        assertEquals("AB", b.readBaudotCharactersShort(2));
    }

    @Test
    public void testReadBaudot() {
        Bits b = Bits.from("111000110011");
        assertEquals("AB", b.readBaudotCharacters(2));
    }

    @Test
    public void readBoolean() {
        Bits b = Bits.from("101");
        assertTrue(b.readBoolean());
        assertFalse(b.readBoolean());
        assertTrue(b.readBoolean());
        assertTrue(b.atEnd());
    }

    @Test(expected = IllegalArgumentException.class)
    public void readBooleanPastEnd() {
        Bits b = Bits.from("101").skip(3);
        b.readBoolean();
    }

    @Test
    public void testToHex() {
        Bits b = Bits.from("01101010");
        assertEquals("6a", b.readHex(2));
    }

    @Test
    public void testEqualsBits() {
        assertFalse(Bits.from("11").equals(null));
        assertFalse(Bits.from("11").equals(Bits.from("10")));
        assertFalse(Bits.from("11").equals(Bits.from("111")));
        assertTrue(Bits.from("11").equals(Bits.from("11")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadBooleanArrayNumBitsZero() {
        Bits.from("11").readBooleanArray(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadBooleanArrayNumBitsTooBig() {
        Bits.from("11").readBooleanArray(3);
    }

    @Test
    public void testReadBooleanArray() {
        Bits b = Bits.from("1110001");
        {
            boolean[] a = b.readBooleanArray(3);
            assertArrayEquals(new boolean[] { true, true, true }, a);
        }
        {
            boolean[] a = b.readBooleanArray(4);
            assertArrayEquals(new boolean[] { false, false, false, true }, a);
        }
    }

    @Test
    public void testIsZero() {
        assertFalse(Bits.from("0001").isZero());
        assertFalse(Bits.from("11111").isZero());
        assertTrue(Bits.from("0000").isZero());
    }

    @Test
    public void testPosition() {
        Bits b = Bits.from("1111");
        assertEquals(0, b.position());
        b.skip(1);
        assertEquals(1, b.position());
    }

    @Test
    public void testReadHex24Bit() {
        assertEquals("ac82ec", Bits.from("101011001000001011101100").readHex(6));
    }

    @Test
    public void testBitsConcat() {
        assertEquals("111000", Bits.from("111").concatWith("000").toBitString());
    }

}
