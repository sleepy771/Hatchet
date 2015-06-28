package org.hatchetproject.manager;

/**
 * Created by filip on 6/27/15.
 */
public class FooObject {

    private String key;
    private int number;

    public FooObject(String key, int number) {
        this.number = number;
        this.key = key;
    }

    public int getNumber() {
        return number;
    }

    public String getString() {
        return key;
    }

    public boolean equals(Object o) {
        if (o == null || o.hashCode() != this.hashCode() || FooObject.class.isInstance(o))
            return false;
        FooObject obj = (FooObject) o;
        return obj.number == number && obj.key.equals(key);
    }
}
