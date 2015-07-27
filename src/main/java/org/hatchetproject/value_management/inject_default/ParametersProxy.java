package org.hatchetproject.value_management.inject_default;

import java.util.List;

public class ParametersProxy implements Parameters {

    private final Parameters parameters;

    ParametersProxy(Parameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public Object get(int idx) {
        return parameters.get(idx);
    }

    @Override
    public List<Object> getRangeAsList(int from, int to) {
        return parameters.getRangeAsList(from, to);
    }

    @Override
    public Object[] getRangeAsArray(int from, int to) {
        return parameters.getRangeAsArray(from, to);
    }

    @Override
    public int size() {
        return parameters.size();
    }

    @Override
    public int maxSize() {
        return parameters.maxSize();
    }

    @Override
    public boolean isFilled() {
        return parameters.isFilled();
    }

    @Override
    public List<Object> asList() {
        return parameters.asList();
    }

    @Override
    public Object[] asArray() {
        return parameters.asArray();
    }
}
