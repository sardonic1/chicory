package com.dylibso.chicory.aot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * A map that maps integer ranges to values. The ranges are inclusive.
 *
 * @param <V> the value type
 */
public class RangeMap<V> {
    private final TreeMap<Range, V> map = new TreeMap<>();

    public void put(Range range, V value) {
        map.put(range, value);
    }

    /**
     * Returns the value associated with the range that contains the given key.
     * @param key the key
     * @return the value associated with the range that contains the given key, or null if no such range exists
     */
    public V get(int key) {
        return map.entrySet().stream()
                .filter(entry -> entry.getKey().contains(key))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);
    }

    /**
     * Removes the entry associated with the given range.
     * @param functionRange the range to remove
     */
    public void remove(Range functionRange) {
        map.entrySet().removeIf(entry -> entry.getKey().equals(functionRange));
    }

    /**
     * Returns the set of entries in the map.
     * @return the entries
     */
    public Set<Map.Entry<Range, V>> entrySet() {
        return map.entrySet();
    }

    /**
     * Returns the values in the map in ascending order by key.
     * @return the values
     */
    public Collection<V> values() {
        return map.values();
    }

    /**
     * Defines an inclusive range of integers.  Natural ordering is by start value.
     */
    public static class Range implements Comparable<Range> {
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
            return Integer.compare(start, other.start);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Range)) return false;
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
}
