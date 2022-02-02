package ru.clevertec.aspect.caching;


import java.util.*;

/**
 * The LRU (Least Recently Used) algorithm is the first to remove objects that have not been used for a long time!
 * <p>
 * This algorithm takes the object that has not been requested for the longest time as the object to be replaced.
 *
 * @param <E> Object type
 */
public class LRUCache<E> implements Cache<E>{
    private final Map<Integer, Node> cache = new HashMap<>();
    private final int capacity;
    private Node heap = null;

    /** @param capacity an integer */
    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    /**
     * @param key   an integer
     * @param value an integer
     */
    @Override
    public void set(int key, E value) {
        Node setNode = new Node(key, value);
        if (cache.get(key) != null) {
            cache.put(key, setNode);
            linkedListUpdate(setNode);
            return;
        }
        if (size() == capacity) {
            cache.remove(heap.key);
            heap = heap.next;
        }

        cache.put(key, setNode);
        linkedListUpdate(setNode);
    }

    /**
     * @param key an integer
     * @return object
     */
    @Override
    public E get(int key) {
        if (cache.containsKey(key)) {
            linkedListUpdate(cache.get(key));
            return cache.get(key).value;
        }
        return null;
    }

    /**
     * @param key an integer
     */
    @Override
    public void delete(int key) {
        Node target = cache.get(key);
        if(target != null) {
            cache.remove(target.key);
            if(target.prev == null){
                heap = target.next;
                return;
            }
            Node prev = target.prev;
            Node next = target.next;
            prev.next = next;
            if(next != null){
                next.prev = prev;
            }
        }
    }

    public int size() {
        return cache.size();
    }

    private void linkedListUpdate(Node setNode) {

        Iterator itr = new Iterator();
        Node targetNode = heap;

        while (itr.hasNext()) {
            targetNode = itr.next();
        }
        if (targetNode != null) {
            targetNode.next = setNode;
            setNode.prev = targetNode;
        } else {
            heap = setNode;
        }
    }

    private class Node {
        Node prev;
        Node next;
        int key;
        E value;

        public Node(int key, E value) {
            this.key = key;
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }

    private class Iterator {
        Node target = heap;

        public boolean hasNext() {
            return target != null && target.next != null;
        }

        public Node next() {
            target = target.next;
            return target.next;
        }
    }
}
