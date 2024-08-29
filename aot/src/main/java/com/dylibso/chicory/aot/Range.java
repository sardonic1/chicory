package com.dylibso.chicory.aot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Defines an inclusive range of integers.  Natural ordering is by start value.
 */
public class Range implements Comparable<Range> {
    private final int start;
    private final int end;

    public Range(int incStart, int incEnd) {
        this.start = incStart;
        this.end = incEnd;
    }

    public boolean contains(int value) {
        return value >= start && value <= end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public List<Range> split(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Count must be greater than 0.");
        } else if (count == 1) {
            return List.of(this);
        }

        int rangeSize = (end - start + 1) / count;
        if (rangeSize == 0) {
            throw new IllegalArgumentException("Count is too large for this range.");
        }

        var ranges = new ArrayList<Range>();
        for (int i = 0; i < count; i++) {
            int newStart = start + i * rangeSize;
            int newEnd = i == count - 1 ? end : newStart + rangeSize - 1;
            ranges.add(new Range(newStart, newEnd));
        }

        return ranges;
    }

    @Override
    public int compareTo(Range other) {
        int comp = Integer.compare(start, other.start);
        if (comp == 0) {
            return Integer.compare(end, other.end);
        } else {
            return comp;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Range)) {
            return false;
        }
        Range range = (Range) o;
        return start == range.start && end == range.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "[" + start + ", " + end + "]";
    }
}
