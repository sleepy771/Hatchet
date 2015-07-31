package org.hatchetproject.settings;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by filip on 7/31/15.
 */
public interface Persistent {

    void load() throws Exception;

    void setInputStream(InputStream stream);

    void setOutputStream(OutputStream stream);

    OutputStream getOutputStream();

    InputStream getInputStream();

    void save() throws Exception;
}
