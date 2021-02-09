package sgb.decoder.rotatingfield;

import com.github.davidmoten.guavamini.Preconditions;

import sgb.decoder.HasFormatter;
import sgb.decoder.Indent;
import sgb.decoder.internal.Fields;
import sgb.decoder.internal.json.JsonSchema;

@Fields(fields = {}, serializedNames = {})
public final class Range implements HasFormatter {

	private final int min;
	private RangeEndType minType;
	private final int max;
	private RangeEndType maxType;

	private Range(int min, RangeEndType minType, int max, RangeEndType maxType) {
		Preconditions.checkNotNull(minType);
		Preconditions.checkNotNull(maxType);
		this.min = min;
		this.minType = minType;
		this.max = max;
		this.maxType = maxType;
	}

	public static Range create(int min, RangeEndType minType, int max, RangeEndType maxType) {
		return new Range(minType == RangeEndType.MISSING ? 0 : min, minType,
				maxType == RangeEndType.MISSING ? 0 : max, maxType);
	}

	public static Range createWithMin(int min, RangeEndType minType) {
		return create(min, minType, 0, RangeEndType.MISSING);
	}

	public int min() {
		return min;
	}

	public RangeEndType minType() {
		return minType;
	}

	public int max() {
		return max;
	}

	public RangeEndType maxType() {
		return maxType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + max;
		result = prime * result + maxType.hashCode();
		result = prime * result + min;
		result = prime * result + minType.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		Range other = (Range) o;
		if (max != other.max)
			return false;
		if (maxType != other.maxType)
			return false;
		if (min != other.min)
			return false;
		if (minType != other.minType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Range [min=" + min + ", minType=" + minType + ", max=" + max + ", maxType="
				+ maxType + "]";
	}

	@Override
	public String toString(Indent indent) {
		String a = minStr();
		String b = maxStr();
		if (!a.isEmpty() && !b.isEmpty()) {
			return a + " and " + b;
		} else {
			return a + b;
		}
	}

	private String minStr() {
		if (minType == RangeEndType.MISSING) {
			return "";
		} else if (minType == RangeEndType.INCLUSIVE) {
			return ">=" + min;
		} else {
			return ">" + min;
		}
	}

	private String maxStr() {
		if (maxType == RangeEndType.MISSING) {
			return "";
		} else if (maxType == RangeEndType.INCLUSIVE) {
			return "<=" + max;
		} else {
			return "<" + max;
		}
	}

	@Override
	public String toJson() {
		StringBuilder b = new StringBuilder();
		if (minType != RangeEndType.MISSING) {
			b.append(JsonSchema.quoted("min") + " : " + min + ", ");
			b.append(JsonSchema.quoted("minInclusive") + " : " + (minType == RangeEndType.INCLUSIVE));
		}
		if (maxType != RangeEndType.MISSING) {
			if (b.length() > 0) {
				b.append(", ");
			}
			b.append(JsonSchema.quoted("max") + " : " + max + ", ");
			b.append(JsonSchema.quoted("maxInclusive") + " : " + (maxType == RangeEndType.INCLUSIVE));
		}
		return "{" + b.toString() + "}";
	}

}
