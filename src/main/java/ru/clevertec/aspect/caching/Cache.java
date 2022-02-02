package ru.clevertec.aspect.caching;


public interface Cache<E> {

    E get(int key);

    void set(int key, E value);

    void delete(int key);

}
