package com.theironyard;



/**
 * Created by vajrayogini on 10/12/16.
 */
public class Hurricane {
    enum Category {
        ONE, TWO, THREE, FOUR, FIVE
    }
    String name;
    String location;
    Category category;
    String image;
    User user;

    public Hurricane(String name, String location, Category category, String image, User user) {
        this.name = name;
        this.location = location;
        this.category = category;
        this.image = image;
        this.user = user;
    }
}
