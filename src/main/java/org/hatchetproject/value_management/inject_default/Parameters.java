package org.hatchetproject.value_management.inject_default;

import java.util.List;

/**
 * Created by filip on 7/14/15.
 */
public interface Parameters {

    Object get(int idx);

    int size();

    int maxSize();

    boolean isFilled();

    List<Object> asList();

    Object[] asArray();
}
