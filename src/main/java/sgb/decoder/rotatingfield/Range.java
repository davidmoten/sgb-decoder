package sgb.decoder.rotatingfield;

import com.github.davidmoten.guavamini.Preconditions;

public final class Range {

    private final int start;
    private RangeEndType startType;
    private final int finish;
    private RangeEndType finishType;

    private Range(int start, RangeEndType startType, int finish, RangeEndType finishType) {
        Preconditions.checkNotNull(startType);
        Preconditions.checkNotNull(finishType);
        this.start = start;
        this.startType = startType;
        this.finish = finish;
        this.finishType = finishType;
    }

    public static Range create(int start, RangeEndType startType, int finish,
            RangeEndType finishType) {
        return new Range(startType == RangeEndType.MISSING ? 0 : start, startType,
                finishType == RangeEndType.MISSING ? 0 : finish, finishType);
    }

    public static Range createWithStart(int start, RangeEndType startType) {
        return create(start, startType, 0, RangeEndType.MISSING);
    }

    public int start() {
        return start;
    }

    public RangeEndType startType() {
        return startType;
    }

    public int finish() {
        return finish;
    }

    public RangeEndType finishType() {
        return finishType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + finish;
        result = prime * result + finishType.hashCode();
        result = prime * result + start;
        result = prime * result + startType.hashCode();
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
        if (finish != other.finish)
            return false;
        if (finishType != other.finishType)
            return false;
        if (start != other.start)
            return false;
        if (startType != other.startType)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Range [start=" + start + ", startType=" + startType + ", finish=" + finish
                + ", finishType=" + finishType + "]";
    }

}
