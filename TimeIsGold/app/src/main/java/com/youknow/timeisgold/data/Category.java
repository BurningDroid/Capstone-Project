package com.youknow.timeisgold.data;

import lombok.Data;

/**
 * Created by Aaron on 02/09/2017.
 */
@Data
public class Category {

    long id;
    String name;
    int color;
    int icon;
    Type type;

    public Category() {
    }

    public Category(String name, int color, int icon, Type type) {
        this.name = name;
        this.color = color;
        this.icon = icon;
        this.type = type;
    }

}
