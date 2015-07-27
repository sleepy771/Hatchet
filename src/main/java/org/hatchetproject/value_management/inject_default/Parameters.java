package org.hatchetproject.value_management.inject_default;

import java.util.List;

public interface Parameters {

    Object get(int idx);

    List<Object> getRangeAsList(int from, int to);

    Object[] getRangeAsArray(int from, int to);

    int size();

    int maxSize();

    boolean isFilled();

    List<Object> asList();

    Object[] asArray();
}
