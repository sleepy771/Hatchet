package org.hatchetproject.value_management.inject_default;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class MutableParametersImpl implements Parameters {

    private Object[] params;
    private Class[] paramTypes;
    private Set<Integer> assigned;

    MutableParametersImpl(Class[] paramTypes) {
        this.paramTypes = new Class[paramTypes.length];
        System.arraycopy(paramTypes, 0, this.paramTypes, 0, paramTypes.length);
        this.params = new Object[paramTypes.length];
        assigned = new HashSet<>();
    }

    public final Object set(int index, Object value) throws ClassCastException {
        try {
            return params[index];
        } finally {
            params[index] = paramTypes[index].cast(value);
            assigned.remove(index);
        }
    }

    public final Map<Integer, Object> setAll(Map<Integer, Object> values) {
        Map<Integer, Object> output = new HashMap<>();
        for (Map.Entry<Integer, Object> valueEntry : values.entrySet()) {
            Object old = set(valueEntry.getKey(), valueEntry.getValue());
            if (null != old) {
                output.put(valueEntry.getKey(), old);
            }
        }
        return output;
    }

    public final Map<Integer, Object> setAll(List<Integer> indexes, List<Object> values) {
        if (indexes.size() != values.size()) {
            throw new InputMismatchException("Indexes size does not match values size");
        }
        if (new HashSet<>(indexes).size() != indexes.size()) {
            throw new InputMismatchException("Indexes does not seems to be unique");
        }
        Map<Integer, Object> valueMap = new HashMap<>();
        Iterator<Integer> indexesIterator = indexes.iterator();
        Iterator<Object> valuesIterator = values.iterator();
        while (indexesIterator.hasNext() && valuesIterator.hasNext()) {
            // TODO maybe prepared set would be better, firs store all values to tmp array then merge them
            int currentIndex = indexesIterator.next();

            Object old = set(currentIndex, valuesIterator.next());
            if (null != old) {
                valueMap.put(currentIndex, old);
            }
        }
        return valueMap;
    }

    public final Map<Integer, Object> setAll(int[] indexes, Object[] values) {
        return setAll(Arrays.asList(ArrayUtils.toObject(indexes)), Arrays.asList(values));
    }

    @Override
    public final Object get(int idx) {
        return params[idx];
    }

    @Override
    public List<Object> getRangeAsList(int from, int to) {
        return null;
    }

    @Override
    public Object[] getRangeAsArray(int from, int to) {
        return new Object[0];
    }

    @Override
    public final int size() {
        return paramTypes.length - assigned.size();
    }

    @Override
    public final int maxSize() {
        return paramTypes.length;
    }

    @Override
    public final boolean isFilled() {
        return assigned.isEmpty();
    }

    @Override
    public List<Object> asList() {
        return new ArrayList<>(Arrays.asList(params));
    }

    @Override
    public Object[] asArray() {
        Object[] tmp = new Object[params.length];
        System.arraycopy(params, 0, tmp, 0, params.length);
        return tmp;
    }
}
