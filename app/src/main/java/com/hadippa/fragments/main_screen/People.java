package com.hadippa.fragments.main_screen;

/**
 * Created by Palak on 25-07-2016.
 */
public class People {

    String name;
    String image;

    public People(String name, String image) {
        this.name = name;
        this.image = image;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
