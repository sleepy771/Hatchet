package org.hatchetproject.sample;

import org.hatchetproject.annotations.HasProperties;
import org.hatchetproject.annotations.InjectValue;
import org.hatchetproject.annotations.InjectValues;
import org.hatchetproject.annotations.IsProperty;

public class Sample {

    private String name;

    private int age;

    private int height, width;

    private float longitude, latitude;

    @IsProperty
    public String info;

    @InjectValues({
            @InjectValue(),
            @InjectValue()
    })
    @IsProperty(name = "name", index = 0)
    public Sample(String name, int age, String info) {
        this.name = name;
        this.age = age;
        this.info = info;
    }

    @IsProperty
    public String getName() {
        return this.name;
    }

    @IsProperty
    public int getAge() {
        return this.age;
    }

    @IsProperty
    public int getWidth() {
        return this.width;
    }

    @IsProperty
    public int getHeight() {
        return this.height;
    }

    @HasProperties({@IsProperty(name = "width"), @IsProperty(name = "height")})
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @IsProperty
    public float getLongitude() {
        return this.longitude;
    }

    @IsProperty
    public float getLatitude() {
        return this.latitude;
    }

    @HasProperties({@IsProperty(name = "latitude", index = 1), @IsProperty(name = "longitude", index = 0)})
    public void setPosition(float longitude, float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
