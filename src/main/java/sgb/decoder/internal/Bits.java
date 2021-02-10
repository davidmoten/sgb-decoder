package sgb.decoder.internal;

import java.math.BigInteger;
import java.util.Arrays;

import com.github.davidmoten.guavamini.Preconditions;

public final class Bits {

	private final boolean[] bits;
	private int pos;

	private Bits(boolean[] bits) {
		this.bits = bits;
	}

	public static Bits from(boolean[] bits) {
		return new Bits(bits);
	}

	public static Bits from(String bitString) {
		boolean[] bits = new boolean[bitString.length()];
		for (int i = 0; i < bits.length; i++) {
			char ch = bitString.charAt(i);
			if (ch == '1') {
				bits[i] = true;
			} else if (ch != '0') {
				throw new IllegalArgumentException("illegal character in bit string " + bitString);
			}
		}
		return from(bits);
	}

	public Bits concatWith(String bitString) {
		return concatWith(Bits.from(bitString));
	}

	public Bits concatWith(Bits b) {
		boolean[] array = Arrays.copyOf(bits, bits.length + b.bits.length);
		System.arraycopy(b.bits, 0, array, bits.length, b.bits.length);
		return Bits.from(array);
	}

	public Bits position(int position) {
		Preconditions.checkArgument(position >= 0 && position <= bits.length);
		this.pos = position;
		return this;
	}

	public Bits skip(int numBits) {
		Preconditions.checkArgument(numBits >= 0);
		return position(pos + numBits);
	}

	public boolean atEnd() {
		return pos == bits.length;
	}

	private int value(int index) {
		return bits[index] ? 1 : 0;
	}

	/**
	 * Assumes the most significant bit is on the left.
	 * 
	 * @param numBits number of bits reads
	 * @return the bits as an integer
	 */
	public int readUnsignedInt(int numBits) {
		Preconditions.checkArgument(numBits > 0);
		Preconditions.checkArgument(pos + numBits <= bits.length);
		int result = 0;
		for (int i = pos; i < pos + numBits; i++) {
			result = 2 * result + value(i);
		}
		pos += numBits;
		return result;
	}

	private static final int SHORT_BAUDOT_CODE_BINARY_LENGTH = 5;
	private static final int BAUDOT_CODE_BINARY_LENGTH = 6;

	public String readBaudotCharactersShort(int numChars) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < numChars; i++) {
			int v = readUnsignedInt(SHORT_BAUDOT_CODE_BINARY_LENGTH);
			s.append(BaudotCode.toCharFromShortCode(v));
		}
		return s.toString();
	}

	public String readBaudotCharacters(int numChars) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < numChars; i++) {
			int v = readUnsignedInt(BAUDOT_CODE_BINARY_LENGTH);
			s.append(BaudotCode.toChar(v));
		}
		return s.toString();
	}

	public boolean readBoolean() {
		Preconditions.checkArgument(!atEnd(), "already at end");
		pos += 1;
		return bits[pos - 1];
	}

	public String readHex(int numChars) {
		return new BigInteger(readBitString(numChars * 4), 2).toString(16);
	}

	public String readBitString(int numBits) {
		String s = readBitsString(pos, numBits);
		pos += numBits;
		return s;
	}

	private String readBitsString(int pos, int numBits) {
		StringBuilder b = new StringBuilder();
		for (int i = pos; i < pos + numBits; i++) {
			b.append(bits[i] ? '1' : '0');
		}
		return b.toString();
	}

	public boolean isEqualTo(Bits b) {
		return b != null && b.bits.length == bits.length && Arrays.equals(b.bits, bits);
	}

	public boolean[] readBooleanArray(int numBits) {
		Preconditions.checkArgument(numBits > 0);
		Preconditions.checkArgument(pos + numBits <= bits.length);
		boolean[] b = Arrays.copyOfRange(bits, pos, pos + numBits);
		pos += numBits;
		return b;
	}

	public Bits readBits(int numBits) {
		return Bits.from(readBooleanArray(numBits));
	}

	public boolean isZero() {
		for (boolean b : bits) {
			if (b) {
				return false;
			}
		}
		return true;
	}

	public int position() {
		return pos;
	}

	public int length() {
		return bits.length;
	}

	public String toBitString() {
		return readBitsString(0, bits.length);
	}
}
