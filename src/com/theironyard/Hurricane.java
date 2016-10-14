package com.theironyard;



/**
 * Created by vajrayogini on 10/12/16.
 */
public class Hurricane {
    enum Category {
        ONE, TWO, THREE, FOUR, FIVE
    }
    int id;
    String name;
    String location;
    Category category;
    String image;
    User user;
    boolean isMe;

    public Hurricane(int id, String name, String location, Category category, String image, User user, boolean isMe) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.category = category;
        this.image = image;
        this.user = user;
        this.isMe = isMe;
    }
}
