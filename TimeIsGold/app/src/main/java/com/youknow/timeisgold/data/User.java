package com.youknow.timeisgold.data;

import java.util.List;

import lombok.Data;

/**
 * Created by Aaron on 30/08/2017.
 */
@Data
public class User {

    private static final String DOT = "_dot_";

    String id;
    String email;
    String name;
    List<Statistics> statisticses;

    public User() {
    }

    public static String getIdFromEmail(String email) {
        return email.replaceAll("\\.", DOT);
    }

    public void setEmail(String email) {
        this.id = email.replaceAll("\\.", DOT);
        this.email = email;
    }
}
