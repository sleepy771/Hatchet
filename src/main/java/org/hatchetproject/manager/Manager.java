package org.hatchetproject.manager;

import org.hatchetproject.exceptions.ManagerException;

import java.util.List;
import java.util.Set;

/**
 * Created by filip on 6/27/15.
 */
public interface Manager<KEY, ELEMENT> extends Iterable<ManagerEntry<KEY, ELEMENT>> {
    void register(ELEMENT element) throws ManagerException;

    void unregister(ELEMENT element);

    boolean isRegistered(ELEMENT element);

    boolean isRegistered(KEY key, ELEMENT element);

    boolean isKeyRegistered(KEY key);

    ELEMENT get(KEY key) throws ManagerException;

    Set<KEY> getRegisteredKeys();

    List<ELEMENT> getRegisteredElements();
}
