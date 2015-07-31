package org.hatchetproject.manager;

import org.hatchetproject.exceptions.ManagerException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public abstract class AbstractManager<KEY, ELEMENT> implements Manager<KEY, ELEMENT> {

    private static class ManagerIterator<KEY, ELEMENT> implements Iterator<ManagerEntry<KEY, ELEMENT>> {

        private Iterator<Map.Entry<KEY, ELEMENT>> iterator;

        ManagerIterator(Map<KEY, ELEMENT> map) {
            iterator = map.entrySet().iterator();
        }

        @Override
        public boolean hasNext() {
            boolean hasNext = iterator.hasNext();
            if (!hasNext) {
                iterator = null;
            }
            return hasNext;
        }

        @Override
        public ManagerEntry<KEY, ELEMENT> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator is dereferenced");
            }
            Map.Entry<KEY, ELEMENT> entry = iterator.next();
            return new ManagerEntryImpl<>(entry);
        }
    }

    private final Map<KEY, ELEMENT> managerMap;

    public AbstractManager() {
        this(new HashMap<>());
        populate(this.managerMap);
    }

    protected AbstractManager(Map<KEY, ELEMENT> map) {
        this.managerMap = map;
    }

    @Override
    public final void register(ELEMENT element) throws ManagerException {
        if (null == element) {
            throw new ManagerException("Invalid argument, null can not be registered");
        }
        KEY key = getKeyForElement(element);
        if (!silentRegister(key, element)) {
            throw new ManagerException("Key [" + keyToString(key) + "] for element is already registered!");
        }
        if (!postRegister(key, element)) {
            throw new ManagerException("Post register failed");
        }
    }

    @Override
    public final void unregister(ELEMENT element) {
        if (null == element) {
            return;
        }
        KEY key = getKeyForElement(element);
        if (remove(key, element)) {
            postUnregister(key, element);
        }
    }

    @Override
    public final Set<KEY> getRegisteredKeys() {
        return new HashSet<>(managerMap.keySet());
    }

    @Override
    public final List<ELEMENT> getRegisteredElements() {
        return new ArrayList<>(managerMap.values());
    }

    @Override
    public Iterator<ManagerEntry<KEY, ELEMENT>> iterator() {
        return new ManagerIterator<>(managerMap);
    }

    protected abstract KEY getKeyForElement(ELEMENT element);

    protected abstract boolean postRegister(KEY key, ELEMENT element);

    protected abstract void postUnregister(KEY key, ELEMENT element);

    protected abstract String verboseKey(KEY key);

    protected abstract String verboseElement(ELEMENT element);

    protected abstract void populate(Map<KEY, ELEMENT> originalMap);

    protected final boolean silentRegister(KEY key, ELEMENT element) {
        if (null == key || null == element) {
            return false;
        }
        if (isKeyRegistered(key)) {
            return !isRegistered(key, element);
        }
        managerMap.put(key, element);
        return true;
    }

    protected final ELEMENT removeKey(KEY key) {
        if (isKeyRegistered(key)) {
            return managerMap.remove(key);
        }
        return null;
    }

    protected final boolean remove(KEY key, ELEMENT element) {
        return !(null == key || null == element) && managerMap.remove(key, element);
    }

    protected final ELEMENT getDirectly(KEY key) {
        return managerMap.get(key);
    }

    protected final boolean containsKey(KEY key) {
        return null != key && managerMap.containsKey(key);
    }

    protected final boolean contains(KEY key, ELEMENT element) {
        if (null == key || null == element) {
            return false;
        }
        ELEMENT registered = managerMap.get(key);
        return null != registered && registered.equals(element);
    }

    protected final String keyToString(KEY key) {
        if (null == key) {
            return "Key: null";
        }
        return verboseKey(key);
    }

    protected final String elementToString(ELEMENT element) {
        if (null == element) {
            return "Element: null";
        }
        return verboseElement(element);
    }
}
