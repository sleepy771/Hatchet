package org.hatchetproject.value_management.inject_default;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by filip on 7/27/15.
 */
interface MutableParameters extends Parameters {

    Object set(int index, Object value) throws ClassCastException;

    Map<Integer, Object> setAll(Map<Integer, Object> values);

    Map<Integer, Object> setAll(List<Integer> indexes, List<Object> values);

    Map<Integer, Object> setAll(int[] indexes, Object[] values);

    Map<Integer, Object> setAll(Object[] values);

    Map<Integer, Object> setAll(List<Object> values);

    Map<Integer, Object> setAllFrom(int beginningIndex, Object[] values);

    Map<Integer, Object> setAllTo(int toIndex, Object[] values);

    int add(Object value);

    List<Integer> addAll(Object[] values);

    List<Integer> addAll(List<Object> values);

    void clear();

    Object remove(int index);

    List<Object> removeAll(int[] indexes);

    List<Object> removeAllIndexes(List<Integer> indexes);

    int remove(Object object);

    List<Integer> removeAll(Object[] objects);

    List<Integer> removeAll(List<Object> objects);
}
