package org.hatchetproject.manager.memory;

import org.apache.log4j.Logger;

/**
 * Created by filip on 6/29/15.
 */
public interface Releasable {
    void free();

    boolean isReleased();

    void setReleaseManager(ReleaseManager manager);

    ReleaseManager getReleaseManager();

    boolean isAssigned();
}
