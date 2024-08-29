package com.dylibso.chicory.aot;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * A map that maps integer ranges to values. The ranges are inclusive.
 *
 * @param <V> the value type
 */
public class RangeMap<V> {
    private final ConcurrentSkipListMap<Range, V> map = new ConcurrentSkipListMap<>();

    public void put(Range range, V value) {
        map.put(range, value);
    }

    /**
     * Returns the value associated with the range that contains the given key.
     *
     * @param key the key
     *
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
     *
     * @param functionRange the range to remove
     */
    public void remove(Range functionRange) {
        map.entrySet().removeIf(entry -> entry.getKey().equals(functionRange));
    }

    /**
     * Returns the set of entries in the map.
     *
     * @return the entries
     */
    public Set<Map.Entry<Range, V>> entrySet() {
        return map.entrySet();
    }

    /**
     * Returns the values in the map in ascending order by key.
     *
     * @return the values
     */
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
