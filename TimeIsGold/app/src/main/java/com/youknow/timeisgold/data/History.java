package com.youknow.timeisgold.data;

import lombok.Data;

/**
 * Created by Aaron on 09/09/2017.
 */
@Data
public class History {
    long activityId;
    String name;
    int color;
    int icon;
    Type type;
    long startTime;
    long endTime;
    long elapsedTime;

    public History(Activity activity) {
        activityId = activity.getId();
        startTime = activity.getStartTime();
        endTime = activity.getEndTime();
        elapsedTime = activity.getRelElapsedTime();
    }

    public void setCategory(Category category) {
        name = category.getName();
        color = category.getColor();
        icon = category.getIcon();
        type = category.getType();
    }
}
