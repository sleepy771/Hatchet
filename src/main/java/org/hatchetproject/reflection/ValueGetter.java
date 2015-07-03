package org.hatchetproject.reflection;

import java.util.Map;

/**
 * Created by filip on 7/2/15.
 */
public interface ValueGetter {
    Map<String, Object> getValues(Scheme scheme, Object object) throws Exception;
}
