package org.hatchetproject.manager;

import org.hatchetproject.exceptions.ManagerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by filip on 6/27/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractManagerTest {

    @InjectMocks
    AbstractManager<String, FooObject> manager;

    FooObject obj1, obj2;

    @Before
    public void setUp() {
        this.obj1 = new FooObject("k1", 1);
        this.obj2 = new FooObject("k2", 2);
        manager = mock(AbstractManager.class);
    }

    @After
    public void tearDown() {
        this.obj2 = null;
        this.obj1 = null;
        this.manager = null;
    }

    @Test
    public void testRegister() throws ManagerException {
        when(manager.getKeyForElement(obj1)).thenReturn("key1");
        when(manager.getKeyForElement(obj2)).thenReturn("key2");
        when(manager.isKeyRegistered("key1")).thenReturn(false);
        when(manager.isKeyRegistered("key2")).thenReturn(false);
        manager.register(obj1);
        manager.register(obj2);
        assertTrue(manager.contains("key1", obj1));
        assertTrue(manager.contains("key2", obj2));
    }

    @Test(expected = ManagerException.class)
    public void testRegisterWithInvalidKey() throws Exception {
        when(manager.getKeyForElement(obj1)).thenReturn("key1");
        when(manager.getKeyForElement(obj2)).thenReturn("key1");
        manager.register(obj1);
        manager.register(obj2);
    }

    @Test
    public void testSilentRegister() throws Exception {
        when(manager.getKeyForElement(obj1)).thenReturn("key1");
        when(manager.isKeyRegistered("key1")).thenReturn(false);
        assertTrue(manager.silentRegister("key1", obj1));
    }
}