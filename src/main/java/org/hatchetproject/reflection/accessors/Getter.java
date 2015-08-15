package org.hatchetproject.reflection.accessors;

public interface Getter extends AccessorExecutor {
    void addListener(ReadyListener<Getter> listener);

    void removeListener(ReadyListener<Getter> listener);

    Object getResult();

    Exception getException();

    boolean hasFailed();

    void clear();
}
