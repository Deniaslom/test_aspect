package ru.clevertec.aspect.caching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.aspect.Person;

import static org.junit.jupiter.api.Assertions.*;

class LFUCacheTest {
    private LFUCache cache = new LFUCache(2);
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
    public void checkGetAfterAdd() {
        cache.get(4);
        cache.get(4);
        cache.get(4);
        cache.get(4);
        cache.get(4);

        cache.get(3);
        cache.get(3);
        cache.get(3);
        cache.get(3);
        cache.set(0, person_0);

        System.out.println(cache.get(0));
        System.out.println(cache.get(4));

    }

}