package com.jcg.jaxb.json;

public class Entity {

    private String name;
    private String rating;

    public Entity() {}

    public Entity(String name, String rating) {

        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }


    public String getRating() {
        return rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}