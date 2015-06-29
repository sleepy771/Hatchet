package org.hatchetproject.manager.memory;

import org.hatchetproject.exceptions.ManagerException;
import org.hatchetproject.manager.Manager;

/**
 * Created by filip on 6/29/15.
 */
public interface ReleaseManager<KEY, RELEASABLE extends Releasable> extends Manager<KEY, RELEASABLE>, Releasable {
    void releaseAll();

    void release(RELEASABLE releasable);

    void release(KEY clazz);

    void release(KEY clazz, RELEASABLE releasable);

    RELEASABLE getOrCreate(KEY clazz) throws ManagerException;
}
