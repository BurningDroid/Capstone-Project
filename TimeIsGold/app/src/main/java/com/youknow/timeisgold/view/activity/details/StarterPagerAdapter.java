package com.youknow.timeisgold.view.activity.details;

import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.view.activity.details.manual.ManualInputFragment;
import com.youknow.timeisgold.view.activity.details.starter.StarterFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Aaron on 10/09/2017.
 */

public class StarterPagerAdapter extends FragmentStatePagerAdapter {

    Context mContext;
    Category mCategory;
    Activity mActivity;

    public StarterPagerAdapter(Context context, FragmentManager supportFragmentManager, Category category, Activity activity) {
        super(supportFragmentManager);

        mContext = context;
        mCategory = category;
        mActivity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        Bundle args = new Bundle();

        switch (position) {
            case 0:
                fragment = new StarterFragment();
                args.putParcelable(mContext.getString(R.string.key_activity), mActivity);
                args.putParcelable(mContext.getString(R.string.key_category), mCategory);
                break;
            case 1:
                fragment = new ManualInputFragment();
                args.putParcelable(mContext.getString(R.string.key_category), mCategory);
                break;
            default:
                fragment = null;
        }

        if (fragment != null) {
            fragment.setArguments(args);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
