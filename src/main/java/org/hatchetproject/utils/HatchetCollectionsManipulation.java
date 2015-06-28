package org.hatchetproject.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by filip on 6/28/15.
 */
public class HatchetCollectionsManipulation {

    public static boolean isEmpty(Collection c) {
        return c == null || c.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static <T> Set<T> union(Set<T> set1, Set<T> set2) {
        Set<T> set = new HashSet<>(set1);
        set.addAll(set2);
        return set;
    }

    public static <KEY, VALUE> Map<KEY, VALUE> merge(Map<KEY, VALUE> original, Map<KEY, VALUE> toBeMerged) {
        Map<KEY, VALUE> map = new HashMap<>(original);
        map.putAll(toBeMerged);
        return map;
    }

    public static <T> boolean isIntersectionEmpty(Collection<T> c1, Collection<T> c2) {
        for (T element : c2) {
            if (c1.contains(element))
                return false;
        }
        return true;
    }
}
