package org.hatchetproject.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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

    public void swap(Object[] array, int i1, int i2) {
        Object tmp = array[i1];
        array[i1] = array[i2];
        array[i2] = tmp;
    }

    public static Iterable<Integer> createIntegersGenerator(int min, int max, int step) {
        return () -> new Iterator<Integer>() {
            int ptr = min;
            @Override
            public boolean hasNext() {
                return ptr < max;
            }
            @Override
            public Integer next() {
                try {
                    return ptr;
                } finally {
                    ptr += step;
                }
            }
        };
    }

    public void rotate(Object[] array, int fromIndex, int toIndex) {

        // 1,2,3,4,5,6,7,8,9 - > 1,2,3,4,5,8,6,7,9

        int signum = (0x10000000 & (toIndex - fromIndex)) * 2 +1;
        int space = Math.abs(toIndex - fromIndex);
        if (fromIndex > toIndex) {
            Object tmp = array[fromIndex];
            for (int k = fromIndex; k > toIndex; k--) {
                array[k] = array[k-1];
            }
            array[toIndex] = tmp;
        } else if (toIndex > fromIndex) {
            Object tmp = array[fromIndex];
            for (int k = fromIndex; k < toIndex; k++) {
                array[k] = array[k+1];
            }
            array[toIndex] = tmp;
        }
    }
}
