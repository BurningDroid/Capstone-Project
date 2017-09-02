package com.youknow.timeisgold.data;

import lombok.Data;

/**
 * Created by Aaron on 30/08/2017.
 */
@Data
public class Activity {
    long id;
    boolean isFavorite;
    long startTime;
    long endTime;
    long spendTime;
    String desc;
    String categoryName;
    String categoryColor;
    String categoryIcon;
    String categoryType;
}
