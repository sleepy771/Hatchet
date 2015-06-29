package org.hatchetproject.manager.memory;

import org.apache.log4j.Logger;
import org.hatchetproject.exceptions.ManagerException;
import org.hatchetproject.manager.ManagerEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public abstract class TimeoutManager<KEY, RELEASABLE extends Releasable> implements ReleaseManager<KEY, RELEASABLE> {

    private static class TimeoutReleasableImpl<RELEASABLE extends Releasable> implements Releasable {

        private RELEASABLE releasable;
        private long timeout;
        private long lastAccess;

        private TimeoutReleasableImpl(RELEASABLE releasable, long timeout) {
            this.lastAccess = System.currentTimeMillis();
            this.timeout = timeout;
            this.releasable = releasable;
        }

        public RELEASABLE access() {
            updateAccessTime();
            return releasable;
        }

        RELEASABLE getReleasable() {
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
            if (releasable == null || releasable.isReleased())
                runCleanup();
            if (releasable == null) {
                return true;
            }
            if (releasable.isReleased()) {
                partialRelease();
                return true;
            }
            return false;
        }

        @SuppressWarnings("unchecked")
        private void runCleanup() {
            if (isAssigned()) {
                getReleaseManager().unregister(getReleasable());
            }
        }

        @Override
        public void setReleaseManager(ReleaseManager manager) {
            releasable.setReleaseManager(manager);
        }

        @Override
        public ReleaseManager getReleaseManager() {
            if (releasable == null)
                return null;
            return releasable.getReleaseManager();
        }

        @Override
        public boolean isAssigned() {
            return releasable.isAssigned();
        }
    }

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            for (TimeoutReleasableImpl releasable : TimeoutManager.this.releasableMap.values()) {
                long time = System.currentTimeMillis();
                if (releasable.getPlanedReleaseAfter() >= time) {
                    releasable.free();
                }
            }
        }
    };

    private static Logger LOGGER = Logger.getLogger(TimeoutManager.class);

    public final static long TIMEOUT = 20000;
    private final static long PERIOD = 10000;

    private static TimeoutManager INSTANCE;
    private Timer timer;
    private Map<KEY, TimeoutReleasableImpl<RELEASABLE>> releasableMap;
    private ReleaseManager releaseManager;
    private boolean freeWasCalled;

    private TimeoutManager() {
        timer = new Timer(true);
        releasableMap = new HashMap<>();
        timer.scheduleAtFixedRate(task, 0, PERIOD);
        freeWasCalled = false;
    }

    @Override
    public void releaseAll() {
        for (KEY key : releasableMap.keySet()) {
            releasableMap.get(key).free();
        }
    }

    @Override
    public boolean isReleased() {
        return freeWasCalled;
    }

    @Override
    public boolean isAssigned() {
        return releaseManager == null;
    }

    @Override
    public ReleaseManager getReleaseManager() {
        return releaseManager;
    }

    @Override
    public void release(RELEASABLE releasable) {
        if (releasable == null) {
            LOGGER.debug("Trying to release null");
            return;
        }
        release(getKey(releasable), releasable);
    }

    public long getReleasableTimeout(KEY key) {
        if (!releasableMap.containsKey(key))
            return -1;
        return releasableMap.get(key).getTimeout();
    }

    public void setTimeout(KEY key, long timeout) {
        if (!releasableMap.containsKey(key))
            return;
        releasableMap.get(key).setTimeout(timeout);
    }

    protected abstract KEY getKey(RELEASABLE releasable);

    public void setDereferencePeriod(long period) {
        this.timer.cancel();
        this.timer.purge();
        this.timer = new Timer(true);
        this.timer.scheduleAtFixedRate(task, (long) 0, period);
    }

    @Override
    public void release(KEY key) {
        release(key, getDirectly(key));
    }

    @Override
    public void release(KEY key, RELEASABLE releasable) {
        if (key == null) {
            if (releasable == null) {
                LOGGER.debug("Trying to release unregistered object for class: null");
                return;
            } else {
                throw new IllegalArgumentException();
            }
        }
        if (releasable.isReleased()) {
            LOGGER.info("Releasable for class:" + keyName(key)
                    + " was released and is not going to be registered");
            return;
        }
        Releasable registered = getDirectly(key);
        if (!(registered == null || registered.isReleased()) && releasable.equals(registered)) {
            releasable.free();
        } else {
            LOGGER.debug("Instance of class: " + keyName(key) + " is not registered!");
        }
    }

    protected abstract String keyName(KEY key);

    protected final RELEASABLE getDirectly(KEY key) {
        TimeoutReleasableImpl<RELEASABLE> timeoutReleasable = releasableMap.get(key);
        if (timeoutReleasable != null && !timeoutReleasable.isReleased()) {
            return timeoutReleasable.access();
        }
        return null;
    }

    @Override
    public void register(RELEASABLE releasable) throws ManagerException {
        register(releasable, TIMEOUT);
    }

    protected abstract String toStringElement(RELEASABLE releasable);

    public void register(RELEASABLE releasable, long timeout) throws ManagerException {
        if (releasable == null) {
            throw new ManagerException("Null can not be registered");
        }
        if (isRegistered(releasable)) {
            throw new ManagerException("Element " + toStringElement(releasable) + " already registered");
        }
        if (releasable.isAssigned()) {
            throw new ManagerException("Can not register assigned element");
        }
        releasableMap.put(getKey(releasable), new TimeoutReleasableImpl<RELEASABLE>(releasable, timeout));
        releasable.setReleaseManager(this);
    }

    @Override
    public void unregister(RELEASABLE releasable) {
        if (isRegistered(releasable)) {
            releasableMap.remove(getKey(releasable), releasable);
        }
    }

    @Override
    public boolean isRegistered(RELEASABLE releasable) {
        if (releasable == null)
            return false;
        KEY key = getKey(releasable);
        return key != null && isRegistered(key, releasable);
    }

    @Override
    public boolean isRegistered(KEY key, RELEASABLE releasable) {
        if (key == null || releasable == null)
            return false;
        RELEASABLE registered = getDirectly(key);
        return registered != null && !registered.isReleased() && registered.equals(releasable);
    }

    @Override
    public boolean isKeyRegistered(KEY key) {
        return key != null && getDirectly(key) != null;
    }

    @Override
    public RELEASABLE get(KEY key) throws ManagerException {
        return null;
    }

    @Override
    public RELEASABLE getOrCreate(KEY key) throws ManagerException {
        RELEASABLE out = getDirectly(key);
        if (out == null || out.isReleased()) {
            out = create(key);
            register(out);
        }
        return out;
    }

    @Override
    public Set<KEY> getRegisteredKeys() {
        return Collections.unmodifiableSet(releasableMap.keySet());
    }

    @Override
    public List<RELEASABLE> getRegisteredElements() {
        List<RELEASABLE> releasables = new ArrayList<>();
        for (TimeoutReleasableImpl<RELEASABLE> releasable : releasableMap.values()) {
            releasables.add(releasable.getReleasable());
        }
        return Collections.unmodifiableList(releasables);
    }

    @Override
    public Iterator<ManagerEntry<KEY, RELEASABLE>> iterator() {
        return null;
    }

    @Override
    public void free() {
        releaseAll();
        timer.cancel();
        timer.purge();
        task = null;
        releasableMap.clear();
        releasableMap = null;
        freeWasCalled = true;
    }

    @Override
    public void setReleaseManager(ReleaseManager manager) {
    }

    protected abstract RELEASABLE create(KEY key) throws ManagerException;
}
