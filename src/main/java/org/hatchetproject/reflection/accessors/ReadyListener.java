package org.hatchetproject.reflection.accessors;

public interface ReadyListener<T> {
    void onReady(T object);
}
