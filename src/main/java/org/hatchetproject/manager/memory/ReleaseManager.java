package org.hatchetproject.manager.memory;

import org.hatchetproject.manager.Manager;

/**
 * Created by filip on 6/29/15.
 */
public interface ReleaseManager extends Manager<Class, Releasable>, Releasable {
    void releaseAll();

    void release(Releasable releasable);

    void release(Class clazz);

    void release(Class clazz, Releasable releasable);
}
