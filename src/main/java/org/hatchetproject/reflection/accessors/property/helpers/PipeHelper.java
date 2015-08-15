package org.hatchetproject.reflection.accessors.property.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PipeHelper implements PropertyHelper {

    private final List<PropertyHelper> pipe;

    public PipeHelper(List<PropertyHelper> helpers) {
        this.pipe = new ArrayList<>(helpers);
    }

    public PipeHelper(PropertyHelper... helpers) {
        this.pipe = Arrays.asList(helpers);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object push(Object o) {
        Object pipeElement = o;
        for (PropertyHelper helper : pipe) {
            pipeElement = helper.push(pipeElement);
        }
        return pipeElement;
    }
}
