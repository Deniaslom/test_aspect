package ru.clevertec.aspect.caching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.aspect.Person;

import static org.junit.jupiter.api.Assertions.*;


class LRUCacheTest {
    private LRUCache cache = new LRUCache(2);
    private final Person person_0 = new Person(0, "Denis_0");
    private final Person person_1 = new Person(1, "Denis_1");
    private final Person person_2 = new Person(2, "Denis_2");
    private final Person person_3 = new Person(3, "Denis_3");
    private final Person person_4 = new Person(4, "Denis_4");
    private final Person person_5 = new Person(5, "Denis_5");
    private final Person person_6 = new Person(6, "Denis_6");
    private final Person person_7 = new Person(7, "Denis_7");

    @BeforeEach
    public void before() {
        cache.set(0, person_0);
        cache.set(1, person_1);
        cache.set(2, person_2);
        cache.set(3, person_3);
        cache.set(4, person_4);
    }

    @Test
    public void checkEntityAfterAdd() {
        assertAll(
                () -> assertFalse(cache.get(0) == person_0),
                () -> assertFalse(cache.get(1) == person_1),
                () -> assertFalse(cache.get(2) == person_2),
                () -> assertTrue(cache.get(3) == person_3),
                () -> assertTrue(cache.get(4) == person_4),
                () -> assertFalse(cache.get(5) == person_5));
    }

    @Test
    public void checkSize() {
        assert (cache.size() == 2);
    }

    @Test
    public void checkSizeAfterFalseDelete() {
        cache.delete(2);
        assert (cache.size() == 2);
    }

    @Test
    public void checkSizeAfterDelete() {
        cache.delete(3);
        assert (cache.size() == 1);
    }

    @Test
    public void checkSizeAfterAdd() {
        cache.set(5, person_5);
        cache.set(6, person_6);
        cache.set(7, person_7);
        assert (cache.size() == 2);
    }

    @Test
    public void checkAdd() {
        cache.set(0, person_0);
        cache.set(1, person_1);

        assert (cache.get(0) == person_0);
        assert (cache.get(1) == person_1);
    }


}