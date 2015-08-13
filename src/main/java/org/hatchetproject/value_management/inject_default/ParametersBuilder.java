package org.hatchetproject.value_management.inject_default;

import org.apache.commons.lang3.ArrayUtils;
import org.hatchetproject.Builder;
import org.hatchetproject.exceptions.ParametersException;
import org.hatchetproject.utils.HatchetCollectionsManipulation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import java.util.stream.Collectors;


public final class ParametersBuilder implements AssignedParameters, MutableParameters, Builder<AssignedParameters> {

    private final static class AssignedParametersImpl extends AssignedParametersWithSetter {

        private final Class[] paramTypes;

        private final Object[] parameters;

        private final Class declaringClass;

        private final String name;

        private final Type type;

        private AssignedParametersImpl(Type type, Class declaringClass, String name, Class[] paramTypes, Object[] parameters) {
            this.type = type;
            this.declaringClass = declaringClass;
            this.paramTypes = paramTypes;
            this.parameters = parameters;
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Type getType() {
            return type;
        }

        @Override
        public Class getDeclaringClass() {
            return declaringClass;
        }

        @Override
        public Object get(int idx) {
            return parameters[idx];
        }

        @Override
        public List<Object> getRangeAsList(int from, int to) {
            return Arrays.asList(getRangeAsArray(from, to));
        }

        @Override
        public Object[] getRangeAsArray(int from, int to) {
            if ((from >= to || 0 >= from || to >= parameters.length)) {
                throw new InputMismatchException("Invalid input");
            }
            Object[] values = new Object[to - from];
            System.arraycopy(this.parameters, from, values, 0, to - from);
            return values;
        }

        @Override
        public int size() {
            return paramTypes.length;
        }

        @Override
        public int maxSize() {
            return paramTypes.length;
        }

        @Override
        public boolean isFilled() {
            return true;
        }

        @Override
        public List<Object> asList() {
            return Arrays.asList(asArray());
        }

        @Override
        public Object[] asArray() {
            Object[] values = new Object[this.parameters.length];
            System.arraycopy(this.parameters, 0, values, 0, this.parameters.length);
            return values;
        }

        @Override
        protected Class[] getParamTypes() {
            return paramTypes;
        }

        @Override
        protected Object[] dirctlyAccessedValues() {
            return parameters;
        }
    }

    private final Method method;

    private final Constructor constructor;

    private final Field field;

    private final AssignedParameters.Type type;

    private final Object[] values;

    private final Class[] parameterTypes;

    private TreeSet<Integer> unassigned;

    public static ParametersBuilder createConstructorParametersBuilder(Constructor constructor) {
        return new ParametersBuilder(Type.CONSTRUCTOR, null, constructor, null);
    }

    public static ParametersBuilder createMethodParametersBuilder(Method method) {
        return new ParametersBuilder(Type.METHOD, method, null, null);
    }

    public static ParametersBuilder createFieldParametersBuilder(Field field) {
        return new ParametersBuilder(Type.FIELD, null, null, field);
    }

    public static ParametersBuilder createCGMethodParametersBuilder(Method method) {
        return new ParametersBuilder(Type.METHOD, method, null, null, new Class[]{method.getReturnType()});
    }

    private ParametersBuilder(AssignedParameters.Type type, Method method, Constructor constructor, Field field) {
        this(type, method, constructor, field, type.getParameterTypes(method, constructor, field));
    }

    private ParametersBuilder(AssignedParameters.Type type, Method method, Constructor constructor, Field field, Class[] parameterTypes) {
        this.method = method;
        this.field = field;
        this.constructor = constructor;
        this.type = type;
        this.values = new Object[parameterTypes.length];
        unassigned = new TreeSet<>();
        for (int num : HatchetCollectionsManipulation.createIntegersGenerator(0, values.length, 1)) {
            unassigned.add(num);
        }
        this.parameterTypes = parameterTypes;
    }

    @Override
    public String getName() {
        return type.getName(method, constructor, field);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Class getDeclaringClass() {
        return type.getDeclaringClass(method, constructor, field);
    }

    @Override
    public Object set(int index, Object value) throws ClassCastException {
        Object old = values[index];
        try {
            values[index] = parameterTypes[index].cast(value);
            unassigned.remove(index);
            return old;
        } catch (ClassCastException e) {
            values[index] = old;
            throw e;
        }
    }

    private Object safeSet(Object[] array, int index, Object value) {
        Object old = values[index];
        try {
            values[index] = parameterTypes[index].cast(value);
            unassigned.remove(index);
            return old;
        } catch (ClassCastException e) {
            values[index] = old;
            throw e;
        }
    }

    // This is unchecked
    private void setValues(Object[] values) {
        System.arraycopy(values, 0, this.values, 0, values.length);
    }

    @Override
    public Map<Integer, Object> setAll(Map<Integer, Object> values) throws ClassCastException {
        Map<Integer, Object> output = new HashMap<>();
        try {
            for (Map.Entry<Integer, Object> valueEntry : values.entrySet()) {
                Object old = set(valueEntry.getKey(), valueEntry.getValue());
                if (null != old) {
                    output.put(valueEntry.getKey(), old);
                }
            }
        } catch (ClassCastException | IndexOutOfBoundsException e) {
            setAll(output); // revert changes and throw exception
            throw e;
        }
        return output;
    }

    @Override
    public Map<Integer, Object> setAll(List<Integer> indexes, List<Object> values) {
        if (indexes.size() != values.size()) {
            throw new InputMismatchException("Indexes size does not match values size");
        }
        if (new HashSet<>(indexes).size() != indexes.size()) {
            throw new InputMismatchException("Indexes does not seems to be unique");
        }
        Map<Integer, Object> valueMap = new HashMap<>();
        try {
            Iterator<Integer> indexesIterator = indexes.iterator();
            Iterator<Object> valuesIterator = values.iterator();
            while (indexesIterator.hasNext() && valuesIterator.hasNext()) {
                int currentIndex = indexesIterator.next();

                Object old = set(currentIndex, valuesIterator.next());
                if (null != old) {
                    valueMap.put(currentIndex, old);
                }
            }
        } catch (ClassCastException | IndexOutOfBoundsException e) {
            setAll(valueMap);
            throw e;
        }
        return valueMap;
    }

    @Override
    public Map<Integer, Object> setAll(int[] indexes, Object[] values) {
        return setAll(Arrays.asList(ArrayUtils.toObject(indexes)), Arrays.asList(values));
    }

    @Override
    public Map<Integer, Object> setAll(Object[] values) {
        return setAllFrom(0, values);
    }

    @Override
    public Map<Integer, Object> setAll(List<Object> values) {
        return setAll(values.toArray());
    }

    @Override
    public Map<Integer, Object> setAllFrom(int beginningIndex, Object[] values) {
        if (values.length + beginningIndex > this.values.length) {
            throw new IllegalArgumentException("values size is too big");
        }
        Map<Integer, Object> oldValues = new HashMap<>();
        try {
            for (int cursor = beginningIndex, length = values.length + beginningIndex; cursor < length; cursor++) {
                oldValues.put(cursor, set(cursor, values[cursor]));
            }
        } catch (ClassCastException | IndexOutOfBoundsException e) {
            setAll(oldValues);
            throw e;
        }
        return oldValues;
    }

    @Override
    public Map<Integer, Object> setAllTo(int toIndex, Object[] values) {
        if (toIndex + 1 < values.length) {
            throw new InputMismatchException("index is less than values length");
        }
        if (toIndex > this.values.length) {
            throw new IndexOutOfBoundsException("index is above the range");
        }
        Map<Integer, Object> oldValues = new HashMap<>();
        try {
            for (int cursor = toIndex - values.length; cursor < toIndex; cursor++) {
                oldValues.put(cursor, set(cursor, values[cursor]));
            }
        } catch (ClassCastException | IndexOutOfBoundsException e) {
            setAll(oldValues);
            throw e;
        }
        return oldValues;
    }

    private int add(int startIndex, Object value) throws ClassCastException {
        if (unassigned.isEmpty()) {
            return -1;
        }
        Integer firstUnassignedIndex = unassigned.ceiling(startIndex);
        if (null == firstUnassignedIndex) {
            return -1;
        }
        values[firstUnassignedIndex] = parameterTypes[firstUnassignedIndex].cast(value);
        unassigned.remove(firstUnassignedIndex);
        return firstUnassignedIndex;
    }

    @Override
    public int add(Object value) { // throw some cool exception
        return add(0, value);
    }

    @Override
    public List<Integer> addAll(Object[] values) {
        List<Integer> indexes = new ArrayList<>();
        int startIndex = 0;
        try {
            for (Object value : values) {
                startIndex = add(startIndex, value);
                indexes.add(startIndex);
            }
        } catch (IndexOutOfBoundsException | ClassCastException e) {
            removeAll(ArrayUtils.toPrimitive((Integer[]) indexes.toArray()));
            throw e;
        }
        return indexes;
    }

    @Override
    public List<Integer> addAll(List<Object> values) {
        return addAll(values.toArray());
    }

    @Override
    public void clear() {
        Arrays.fill(values, null);
    }

    @Override
    public Object remove(int index) {
        Object old = values[index];
        unassigned.add(index);
        return old;
    }

    @Override
    public List<Object> removeAll(int[] indexes) {
        List<Object> objects = new ArrayList<>(indexes.length);
        for (int index : indexes) {
            remove(index);
        }
        return objects;
    }

    @Override
    public List<Object> removeAllIndexes(List<Integer> indexes) {
        return new ArrayList<>(indexes.stream().map(this::remove).collect(Collectors.toList()));
    }

    private int remove(int startIndex, Object object) {
        int index;
        for (index = startIndex; values.length > index; index++) {
            if (values[index].equals(object)) {
                values[index] = null;
                return index;
            }
        }
        return -1;
    }

    @Override
    public int remove(Object object) throws NoSuchElementException {
        int idx = remove(0, object);
        checkRemoveIndex(idx);
        return idx;
    }

    @Override
    public List<Integer> removeAll(Object[] objects) throws NoSuchElementException {
        List<Integer> indexes = new ArrayList<>();
        int index = 0;
        for (Object object : objects) {
            index = remove(index, object);
            checkRemoveIndex(index);
            indexes.add(index);
        }
        return indexes;
    }

    private void checkRemoveIndex(int index) {
        if (0 > index) {
            throw new NoSuchElementException("Any equivalent element wasn't found in array");
        }
    }

    @Override
    public List<Integer> removeAll(List<Object> objects) throws NoSuchElementException {
        return removeAll(objects.toArray());
    }

    @Override
    public Object get(int idx) {
        return values[idx];
    }

    @Override
    public List<Object> getRangeAsList(int from, int to) {
        return Arrays.asList(getRangeAsArray(from, to));
    }

    @Override
    public Object[] getRangeAsArray(int from, int to) {
        Object[] tmpArray = new Object[to - from];
        System.arraycopy(values, from, tmpArray, 0, to - from);
        return tmpArray;
    }

    @Override
    public int size() {
        return maxSize() - unassigned.size();
    }

    @Override
    public int maxSize() {
        return values.length;
    }

    @Override
    public boolean isFilled() {
        if (null == unassigned || unassigned.isEmpty()) {
            unassigned = null;
            return true;
        }
        return false;
    }

    @Override
    public List<Object> asList() {
        return Arrays.asList(asArray());
    }

    @Override
    public Object[] asArray() {
        Object[] tmpArray = new Object[values.length];
        System.arraycopy(this.values, 0, tmpArray, 0, values.length);
        return tmpArray;
    }

    @Override
    public AssignedParameters build() throws ParametersException {
        if (!isFilled()) {
            throw new ParametersException("Parameters not assigned yet");
        }
        return new AssignedParametersImpl(type, getDeclaringClass(), getName(), parameterTypes, values);
    }

    public Field getField() {
        return field;
    }

    public Method getMethod() {
        return method;
    }

    public Constructor getConstructor() {
        return constructor;
    }
}
