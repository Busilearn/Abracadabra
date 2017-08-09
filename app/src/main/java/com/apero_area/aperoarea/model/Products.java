package com.apero_area.aperoarea.model;

import io.realm.RealmObject;

/**
 * Created by micka on 09-Aug-17.
 */

public class Products extends RealmObject {

    private String          name;
    private String          description;
    private String          picture;

    public String getName() { return name; }
    public void   setName(String name) { this.name = name; }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
