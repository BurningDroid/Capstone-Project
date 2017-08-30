package com.youknow.timeisgold.data;

import lombok.Data;

/**
 * Created by Aaron on 30/08/2017.
 */
@Data
public class Activity {
    long id;
    String icon;
    boolean isFavorite;
    Type type;
    long startTime;
    long endTime;
    long spendTime;
    String category;
    String title;
    String tag;
    String desc;

}
