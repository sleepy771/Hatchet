package org.hatchetproject.manager.memory;

import org.apache.log4j.Logger;
import org.hatchetproject.exceptions.ManagerException;
import org.hatchetproject.manager.ManagerEntry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class TimeoutManager implements ReleaseManager {

    private static class TimeoutReleasableImpl implements Releasable {

        private Releasable releasable;
        private long timeout;
        private long lastAccess;

        private TimeoutReleasableImpl(Releasable releasable, long timeout) {
            this.lastAccess = System.currentTimeMillis();
            this.timeout = timeout;
            this.releasable = releasable;
        }

        public Releasable access() {
            updateAccessTime();
            return releasable;
        }

        Releasable getReleasable() {
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
                updateAccessTime();
            }
        }

        private void updateAccessTime() {
            this.lastAccess = System.currentTimeMillis();
        }

        public long getTimeout() {
            return timeout;
        }

        public long getLastAccess() {
            return lastAccess;
        }

        public long getPlanedReleaseAfter() {
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

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            for (TimeoutReleasableImpl releasable : TimeoutManager.this.releasableMap.values()) {
                long time = System.currentTimeMillis();
                if (releasable.getPlanedReleaseAfter() >= time) {
                    // TODO FIX release of TimeoutManager.this
                    releasable.free();
                    if (releasable.getReleasable().equals(TimeoutManager.this))
                        break;
                }
            }
        }
    };

    private static Logger LOGGER = Logger.getLogger(TimeoutManager.class);

    public final static long TIMEOUT = 20000;
    private final static long PERIOD = 10000;

    private static TimeoutManager INSTANCE;
    private Timer timer;
    private long period;
    private Map<Class<? extends Releasable>, TimeoutReleasableImpl> releasableMap;

    private TimeoutManager() {
        timer = new Timer(true);
        releasableMap = new HashMap<>();
        releasableMap.put(getOriginalReleasable(this), new TimeoutReleasableImpl(this, TIMEOUT));
        this.period = PERIOD;
        timer.scheduleAtFixedRate(task, 0, PERIOD);
    }

    @Override
    public void releaseAll() {

    }

    @Override
    public void release(Releasable releasable) {
        if (releasable == null) {
            LOGGER.debug("Trying to release null");
            return;
        }
        release(getOriginalReleasable(releasable), releasable);
    }

    public long getReleasableTimeout(Class<? extends Releasable> clazz) {
        if (!releasableMap.containsKey(clazz))
            return -1;
        return releasableMap.get(clazz).getTimeout();
    }

    public void setTimeout(Class<? extends Releasable> clazz, long timeout) {
        if (!releasableMap.containsKey(clazz))
            return;
        releasableMap.get(clazz).setTimeout(timeout);
    }

    private Class<? extends Releasable> getOriginalReleasable(Releasable releasable) {
        if (releasable == null || releasable.isReleased())
            throw new IllegalArgumentException("Hipster instance alert: Was released, before it was cool!");
        if (TimeoutReleasableImpl.class == releasable.getClass()) {
            TimeoutReleasableImpl timeoutReleasable = (TimeoutReleasableImpl) releasable;
            return timeoutReleasable.getReleasable().getClass();
        }
        return releasable.getClass();
    }

    public void setDereferencePeriod(long period) {
        this.timer.cancel();
        this.timer.purge();
        this.timer = new Timer(true);
        this.timer.scheduleAtFixedRate(task, (long) 0, period);
    }

    @Override
    public void release(Class clazz) {
        release(clazz, releasableMap.get(clazz));
    }

    @Override
    public void release(Class clazz, Releasable releasable) {
        if (clazz == null) {
            if (releasable == null) {
                LOGGER.debug("Trying to release unregistered object for class: null");
                return;
            } else {
                throw new IllegalArgumentException();
            }
        }
        if (releasable.isReleased()) {
            LOGGER.info("Releasable for class:" + clazz.getName()
                    + " was released and is not going to be registered");
            return;
        }
        Releasable registered = releasableMap.get(clazz);
        if (!(registered == null || registered.isReleased()) && releasable.equals(registered)) {
            releasable.free();
        } else {
            LOGGER.debug("Instance of class: " + clazz.getName() + " is not registered!");
        }
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
}
