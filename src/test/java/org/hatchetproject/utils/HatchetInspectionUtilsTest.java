package org.hatchetproject.utils;

import org.hatchetproject.utils.mock.MockObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by filip on 7/2/15.
 */
public class HatchetInspectionUtilsTest {



    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testIsGetter() throws Exception {
        Method method = MockObject.class.getDeclaredMethod("getInt");
        assertTrue(HatchetInspectionUtils.isGetter(method));
    }

    @Test
    public void testIsGetterPrivate() throws Exception {
        Method method = MockObject.class.getDeclaredMethod("getBoolean");
        assertTrue(HatchetInspectionUtils.isGetter(method));
    }

    @Test
    public void testIsGetterOnNonGetterMethod() throws Exception {
        Method method = MockObject.class.getDeclaredMethod("setValue", int.class);
        assertFalse(HatchetInspectionUtils.isGetter(method));
    }

    @Test
    public void testIsSetter() throws Exception {
        Method method = MockObject.class.getDeclaredMethod("setValue", int.class);
        assertTrue(HatchetInspectionUtils.isSetter(method));
    }

    @Test
    public void testIsSetterPrivate() throws Exception {
        Method method = MockObject.class.getDeclaredMethod("setter", boolean.class);
        assertTrue(HatchetInspectionUtils.isSetter(method));
    }

    @Test
    public void testIsSetterProtected() throws Exception {
        Method method = MockObject.class.getDeclaredMethod("protectedSetter", char.class);
        assertTrue(HatchetInspectionUtils.isSetter(method));
    }

    @Test
    public void testIsNotSetter() throws Exception {
        Method method = MockObject.class.getDeclaredMethod("getBoolean");
        assertTrue(HatchetInspectionUtils.isGetter(method));
    }

    @Test
    public void testIsAccessibleGetter() throws Exception {
        Method method = MockObject.class.getDeclaredMethod("getInt");
        assertTrue(HatchetInspectionUtils.isAccessibleGetter(method));
    }

    @Test
    public void testIsAccessibleGetterPrivate() throws Exception {
        Method method = MockObject.class.getDeclaredMethod("getBoolean");
        assertFalse(HatchetInspectionUtils.isAccessibleGetter(method));
    }

    @Test
    public void testIsAccessibleSetter() throws Exception {

    }

    @Test
    public void testIsEmptyNULL() throws Exception {
        String str = null;
        assertTrue(HatchetInspectionUtils.isEmpty(str));
    }

    @Test
    public void testIsEmptyEmpty() throws Exception {
        String str = "";
        assertTrue(HatchetInspectionUtils.isEmpty(str));
    }

    @Test
    public void testIsEmptyWhitespaces() throws Exception {
        String str = " \n";
        assertTrue(HatchetInspectionUtils.isEmpty(str));
    }

    @Test
    public void testIsEmptyWhitespacesAndText() throws Exception {
        String str = " \ntext";
        assertFalse(HatchetInspectionUtils.isEmpty(str));
    }

    @Test
    public void testIsValidPropertyName() throws Exception {
        String propertyName = "-";
        assertTrue(HatchetInspectionUtils.isValidPropertyName(propertyName));
    }

    @Test
    public void testIsValidPropertyNameInvalid() throws Exception {
        String propertyName = "#";
        assertFalse(HatchetInspectionUtils.isValidPropertyName(propertyName));
    }

    @Test
    public void testIsValidPropertyNameValidWithPrintable() throws Exception {
        String propertyName = "n#az";
        assertTrue(HatchetInspectionUtils.isValidPropertyName(propertyName));
    }

    @Test
    public void testIsValidPropertyNameValidStandard() throws Exception {
        String propertyName = "name";
        assertTrue(HatchetInspectionUtils.isValidPropertyName(propertyName));
    }

    @Test
    public void testCreatePropertyName() throws Exception {
        String methodName = "getName";
        String propertyName = "-";
        assertEquals("name", HatchetInspectionUtils.createPropertyName(methodName, propertyName));
    }

    @Test
    public void testCreatePropertyNameWithDeffinedPropertyName() throws Exception {
        String methodName = "getName";
        String propertyName = "LastName";
        assertEquals("LastName", HatchetInspectionUtils.createPropertyName(methodName, propertyName));
    }

    @Test
    public void testCreatePropertyNameSetter() throws Exception {
        String methodName = "setName";
        String propertyName = "-";
        assertEquals("name", HatchetInspectionUtils.createPropertyName(methodName, propertyName));
    }

    @Test
    public void testCreatePropertyWithoutDefaultPrefix() throws Exception {
        String methodName = "Name";
        String propertyName = "-";
        assertEquals("name", HatchetInspectionUtils.createPropertyName(methodName, propertyName));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreatePropertyNameWithInvalidPropertyName() throws Exception {
        String methodName = "getName";
        String propertyName = "#";
        HatchetInspectionUtils.createPropertyName(methodName, propertyName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreatePropertyNameWithInvalidPropertyNameOther() throws Exception {
        String methodName = "getName";
        String propertyName = "#name";
        HatchetInspectionUtils.createPropertyName(methodName, propertyName);
    }
}