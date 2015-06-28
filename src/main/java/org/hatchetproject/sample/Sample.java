package org.hatchetproject.sample;

import org.hatchetproject.annotations.InjectDefault;
import org.hatchetproject.annotations.InjectMultiple;
import org.hatchetproject.annotations.Properties;
import org.hatchetproject.annotations.Property;
import org.hatchetproject.reflection.constants.Nothing;

public class Sample {

    private String name;

    private int age;

    private int height, width;

    private float longitude, latitude;

    @Property
    public String info;

    @InjectMultiple({
            @InjectDefault(injectByClass = Nothing.class),
            @InjectDefault(injectByClass = Nothing.class)
    })
    @Property(name = "name", index = 0)
    public Sample(String name, int age, String info) {
        this.name = name;
        this.age = age;
        this.info = info;
    }

    @Property
    public String getName() {
        return this.name;
    }

    @Property
    public int getAge() {
        return this.age;
    }

    @Property
    public int getWidth() {
        return this.width;
    }

    @Property
    public int getHeight() {
        return this.height;
    }

    @Properties({@Property(name = "width"), @Property(name = "height")})
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Property
    public float getLongitude() {
        return this.longitude;
    }

    @Property
    public float getLatitude() {
        return this.latitude;
    }

    @Properties({@Property(name = "latitude", index = 1), @Property(name = "longitude", index = 0)})
    public void setPosition(float longitude, float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
