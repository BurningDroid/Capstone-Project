package com.youknow.timeisgold.data.source;

import com.youknow.timeisgold.data.Activity;

/**
 * Created by Aaron on 31/08/2017.
 */

public interface ActivityDataSource {

    void saveActivity(Activity activity);
    Activity getActivity(long id);
    void deleteActivity(long id);

}
