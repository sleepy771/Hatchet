package org.hatchetproject.manager.memory;

import org.hatchetproject.exceptions.ManagerException;
import org.hatchetproject.manager.ManagerEntry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by filip on 6/29/15.
 */
public class TimeoutManager extends TimerTask implements ReleaseManager {

    private static class TimeoutReleasableImpl implements Releasable {

        private Releasable releasable;
        private long timeout;
        private long lastAccess;

        public Releasable access() {
            lastAccess = System.currentTimeMillis();
            return releasable;
        }

        @Override
        public void free() {
            releasable.free();
            partialRelease();
        }

        private void partialRelease() {
            releasable = null;
            timeout = -1;
            lastAccess = -1;
        }

        public void setTimeout(long timeout) {
            if (timeout > 0) {
                this.timeout = timeout;
            }
        }

        public long getTimeout() {
            return timeout;
        }

        public long getLastAccess() {
            return lastAccess;
        }

        public long planedReleaseAfter() {
            return timeout + lastAccess;
        }

        @Override
        public boolean isReleased() {
            if (releasable == null)
                return true;
            if (releasable.isReleased()) {
                partialRelease();
                return true;
            }
            return false;
        }

        @Override
        public void setReleaseManager(ReleaseManager manager) {
            releasable.setReleaseManager(manager);
        }
    }


    public final static long TIMEOUT = 20000;
    private final static long PERIOD = 10000;

    private static TimeoutManager INSTANCE;
    private Timer timer;
    private long period = PERIOD;
    private Map<Class<? extends Releasable>, TimeoutReleasableImpl> releasableMap;

    public TimeoutManager(long period, long timeout) {
        timer = new Timer();
        timer.scheduleAtFixedRate(this, 0, period);
        releasableMap = new HashMap<>();

    }

    public TimeoutManager() {
        this(PERIOD, TIMEOUT);
    }

    @Override
    public void releaseAll() {

    }

    @Override
    public void release(Releasable releasable) {

    }

    @Override
    public void release(Class clazz) {

    }

    @Override
    public void release(Class clazz, Releasable releasable) {

    }

    @Override
    public void register(Releasable releasable) throws ManagerException {

    }

    public void register(Releasable releasable, long timeout) throws ManagerException {

    }

    @Override
    public void unregister(Releasable releasable) {

    }

    @Override
    public boolean isRegistered(Releasable releasable) {
        return false;
    }

    @Override
    public boolean isRegistered(Class aClass, Releasable releasable) {
        return false;
    }

    @Override
    public boolean isKeyRegistered(Class aClass) {
        return false;
    }

    @Override
    public Releasable get(Class aClass) throws ManagerException {
        return null;
    }

    @Override
    public Set<Class> getRegisteredKeys() {
        return null;
    }

    @Override
    public List<Releasable> getRegisteredElements() {
        return null;
    }

    @Override
    public Iterator<ManagerEntry<Class, Releasable>> iterator() {
        return null;
    }

    @Override
    public void free() {

    }

    @Override
    public boolean isReleased() {
        return INSTANCE == null;
    }

    @Override
    public void setReleaseManager(ReleaseManager manager) {

    }

    public static TimeoutManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TimeoutManager();
        }
        return INSTANCE;
    }

    @Override
    public void run() {

    }
}
