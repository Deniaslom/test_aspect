package ru.clevertec.aspect.caching;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The LFU (Least Frequently Used) algorithm is the first to remove
 * the object with the least number of visits in a certain period of time!
 * <p>
 * This algorithm selects the least requested object as the replacement object
 *
 * @param <E> Object type
 */
public class LFUCache<E> implements Cache<E> {
    private final int capacity;
    private final Map<Integer, HitRate> cache = new HashMap<>();
    private final Map<Integer, E> KV = new HashMap<>();

    /**
     * @param capacity, an integer
     */
    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    /**
     * @param key, an integer
     * @param value, an integer
     */
    public void set(int key, E value) {
        E v = KV.get(key);
        if (v == null) {
            if (cache.size() == capacity) {
                Integer k = getKickedKey();
                KV.remove(k);
                cache.remove(k);
            }
            cache.put(key, new HitRate(key, 1, System.nanoTime()));
        } else {
            HitRate hitRate = cache.get(key);
            hitRate.hitCount += 1;
            hitRate.lastTime = System.nanoTime();
        }
        KV.put(key, value);
    }

    /**
     * Getting an object from the cache
     *
     * @param key an integer
     * @return cache object
     */
    public E get(int key) {
        E v = KV.get(key);
        if (v != null) {
            HitRate hitRate = cache.get(key);
            hitRate.hitCount += 1;
            hitRate.lastTime = System.nanoTime();
            return v;
        }
        return null;
    }

    /**
     * Deleting an object in the cache
     *
     * @param key an integer
     */
    public void delete(int key) {
        KV.remove(key, KV.get(key));
    }

    /**
     * @return replacement key
     */
    private Integer getKickedKey() {
        HitRate min = Collections.min(cache.values());
        return min.key;
    }



    /**
     * hitCount - number of hits
     * lastTime - last access time
     */
    static class HitRate implements Comparable<HitRate> {
        Integer key;
        Integer hitCount;
        Long lastTime;

        public HitRate(Integer key, Integer hitCount, Long lastTime) {
            this.key = key;
            this.hitCount = hitCount;
            this.lastTime = lastTime;
        }

        public int compareTo(HitRate o) {
            int hr = hitCount.compareTo(o.hitCount);
            return hr != 0 ? hr : lastTime.compareTo(o.lastTime);
        }
    }

}