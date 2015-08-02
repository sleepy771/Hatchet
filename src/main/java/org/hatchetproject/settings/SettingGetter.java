package org.hatchetproject.settings;

import java.io.Serializable;

/**
 * Created by filip on 7/31/15.
 */
public interface SettingGetter extends Serializable {

    String getName();

    Class getType();

   Object getValue();
}
