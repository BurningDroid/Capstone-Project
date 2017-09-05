package com.youknow.timeisgold.view.activity.addedit;

import com.youknow.timeisgold.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Aaron on 03/09/2017.
 */

public class CategoryDialog extends DialogFragment {

    CategoryListener mCategoryListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_category, null);
        GridView gvCategories = (GridView) rootView.findViewById(R.id.gv_categories);
        gvCategories.setAdapter(new CategoryAdapter(getContext()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView).setTitle(getString(R.string.select_category_icon));

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCategoryListener = (CategoryListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement NoticeDialogListener");
        }
    }

    interface CategoryListener {
        void categorySelected(int icon);
    }

    private class CategoryAdapter extends BaseAdapter {

        Context mContext;
        int[] categories = null;

        public CategoryAdapter(Context context) {
            mContext = context;
            categories = new int[]{R.drawable.ic_category_airplane,
                    R.drawable.ic_category_beach,
                    R.drawable.ic_category_bike,
                    R.drawable.ic_category_boat,
                    R.drawable.ic_category_brightness,
                    R.drawable.ic_category_brush,
                    R.drawable.ic_category_build,
                    R.drawable.ic_category_cake,
                    R.drawable.ic_category_call,
                    R.drawable.ic_category_camera,
                    R.drawable.ic_category_car,
                    R.drawable.ic_category_casino,
                    R.drawable.ic_category_child,
                    R.drawable.ic_category_city,
                    R.drawable.ic_category_cleaning,
                    R.drawable.ic_category_create,
                    R.drawable.ic_category_cut,
                    R.drawable.ic_category_delete,
                    R.drawable.ic_category_draft,
                    R.drawable.ic_category_drink,
                    R.drawable.ic_category_eating,
                    R.drawable.ic_category_exercise,
                    R.drawable.ic_category_explore,
                    R.drawable.ic_category_game,
                    R.drawable.ic_category_gift,
                    R.drawable.ic_category_golf,
                    R.drawable.ic_category_group_work,
                    R.drawable.ic_category_hospital,
                    R.drawable.ic_category_info,
                    R.drawable.ic_category_language,
                    R.drawable.ic_category_laundry,
                    R.drawable.ic_category_library,
                    R.drawable.ic_category_love,
                    R.drawable.ic_category_map,
                    R.drawable.ic_category_mic,
                    R.drawable.ic_category_money,
                    R.drawable.ic_category_motorcycle,
                    R.drawable.ic_category_move,
                    R.drawable.ic_category_movie,
                    R.drawable.ic_category_music,
                    R.drawable.ic_category_pet,
                    R.drawable.ic_category_phone,
                    R.drawable.ic_category_pool,
                    R.drawable.ic_category_pregnant,
                    R.drawable.ic_category_radio,
                    R.drawable.ic_category_reading,
                    R.drawable.ic_category_rowing,
                    R.drawable.ic_category_run,
                    R.drawable.ic_category_seat,
                    R.drawable.ic_category_shopping,
                    R.drawable.ic_category_shower,
                    R.drawable.ic_category_sleep,
                    R.drawable.ic_category_sns,
                    R.drawable.ic_category_star,
                    R.drawable.ic_category_study,
                    R.drawable.ic_category_supervisor,
                    R.drawable.ic_category_tea_time,
                    R.drawable.ic_category_terrain,
                    R.drawable.ic_category_tv,
                    R.drawable.ic_category_video,
                    R.drawable.ic_category_wc,
                    R.drawable.ic_category_whatshot,
                    R.drawable.ic_category_work};
        }

        @Override
        public int getCount() {
            return categories.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ImageView ivCategory;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                ivCategory = new ImageView(mContext);
                ivCategory.setLayoutParams(new GridView.LayoutParams(200, 200));
                ivCategory.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ivCategory.setPadding(8, 8, 8, 8);
            } else {
                ivCategory = (ImageView) convertView;
            }

            ivCategory.setImageResource(categories[position]);
            ivCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCategoryListener.categorySelected(categories[position]);
                    CategoryDialog.this.dismiss();
                }
            });
            return ivCategory;
        }
    }
}
