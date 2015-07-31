package org.hatchetproject.value_management;

import org.hatchetproject.annotations.InjectDefault;
import org.hatchetproject.exceptions.ManagerException;
import org.hatchetproject.exceptions.ParserException;
import org.hatchetproject.manager.DefaultAbstractManager;
import org.hatchetproject.utils.HatchetInspectionUtils;

import java.util.Map;

public class ParsersManager extends DefaultAbstractManager<String, HatchetParser> {

    private static ParsersManager INSTANCE;

    @Override
    protected String getKeyForElement(final HatchetParser hatchetParser) {
        return hatchetParser.getDeclaringClass().getName();
    }

    @Override
    protected String verboseElement(final HatchetParser hatchetParser) {
        return "Parser: {" + hatchetParser.getDeclaringClass().getName() + "<->" + "java.lang.String }";
    }

    @Override
    protected String verboseKey(String s) {
        return s;
    }

    public static ParsersManager getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new ParsersManager();
        }
        return INSTANCE;
    }

    public static void free() {
        INSTANCE = null;
    }

    public static Object parse(String className, String value) throws ParserException {
        try {
            return getInstance().get(className).parse(value);
        } catch (ManagerException e) {
            throw new ParserException("HatchetParser class for object type " + className + " not found", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static String toString(Object value) throws ParserException {
        try {
            String className = value.getClass().getName();
            return className + ":"
                    + getInstance().get(className).toString(value);
        } catch (ManagerException e) {
            throw new ParserException("Class not found", e);
        }
    }
}
