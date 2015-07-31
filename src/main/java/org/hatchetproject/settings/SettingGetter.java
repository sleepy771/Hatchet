package org.hatchetproject.settings;

/**
 * Created by filip on 7/31/15.
 */
public interface SettingGetter {

    String getName();

    Class getType();

    Object getValue();
}
