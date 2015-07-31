package org.hatchetproject.settings;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by filip on 7/31/15.
 */
public interface Persistent {

    void load(InputStream inputStream) throws Exception;

    OutputStream save() throws Exception;
}
