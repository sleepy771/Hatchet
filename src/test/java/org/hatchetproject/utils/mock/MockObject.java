package org.hatchetproject.utils.mock;

/**
 * Created by filip on 7/2/15.
 */
public class MockObject {

    public MockObject() {
    }

    protected MockObject(int a) {
    }

    private MockObject(int a, int b) {
    }

    public int getInt() {
        return 1;
    }

    public void getInt(int value) {
    }

    protected int getProtectedInt() {
        return 10;
    }

    private boolean getBoolean() {
        return true;
    }

    public int setValue(int value) {
        return value;
    }

    private void setter(boolean value) {
    }

    protected void protectedSetter(char a) {
    }
}
