package org.hatchetproject.value_management.inject_default;

import org.hatchetproject.utils.HatchetInspectionUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvGenerator implements MapValueGenerator {

    private File csvFile;

    public CsvGenerator(File csvFile) {
        this.csvFile = csvFile;
    }

    @Override
    public void populate(Map<String, Object> valuesMap) {

    }

    static class CsvLine {

        private static Pattern INVOKE_PATTERN = Pattern.compile("^([a-zA-Z]+(\\.[a-zA-Z]+)+)::([a-zA-Z]\\w+)\\s*[^\\(]");
        private static Pattern ARGUMENT_TYPES = Pattern.compile("\\(([a-zA-Z]+(\\.[a-zA-Z]+)+),?");

        private String name;
        private String className;
        private String stringValue;
        private String invoke;

        private Class type;
        private Class declaringInvokeClass;
        private Method invokeMethod;
        private Object value;

        private CsvLine(String name, String className, String value, String invoke) {
            this.name = name;
            this.className = className;
            this.stringValue = value;
            this.invoke = invoke;
        }

        boolean hasInvoke() {
            return !HatchetInspectionUtils.isEmpty(invoke);
        }

        public Class getType() throws ClassNotFoundException {
            if (type == null) {
                if (HatchetInspectionUtils.isEmpty(className))
                    throw new IllegalArgumentException();
                type = Class.forName(className);
            }
            return type;
        }

        public Object getObject() {
            if (value == null) {

            }
            return value;
        }

        public String getName() {
            return name;
        }

        private String invokeClassName() {
            if (!hasInvoke())
                return null;
            return invoke.split("::")[0];
        }

        private String invokeMethodName() {
            if (!hasInvoke())
                return null;
            return invoke.split("::")[1];
        }

        // java.lang.Integer::parseInt(java.lang.String)
        // java.lang.Integer::valueOf(java.lang.String, int)
        private void findInvoke() throws ClassNotFoundException {
            Matcher matcher = INVOKE_PATTERN.matcher(this.invoke);
            String className = null;
            String methodName = null;
            List<String> arguments = new ArrayList<>();
            if (matcher.find()) {
                className = matcher.group(1);
                methodName = matcher.group(3);
                return;
            }
            Matcher argMatcher = ARGUMENT_TYPES.matcher(this.invoke);
            while(argMatcher.find()) {
                arguments.add(argMatcher.group());
            }
        }
    }
}
