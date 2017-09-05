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

public class ColorDialog extends DialogFragment {

    ColorListener mColorListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_color, null);
        GridView gvColors = (GridView) rootView.findViewById(R.id.gv_colors);
        gvColors.setAdapter(new ColorAdapter(getContext()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView).setTitle(getString(R.string.select_category_color));

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mColorListener = (ColorListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement NoticeDialogListener");
        }
    }

    interface ColorListener {
        void colorSelected(int color);
    }

    private class ColorAdapter extends BaseAdapter {

        Context mContext;
        int[] colors = null;

        public ColorAdapter(Context context) {
            mContext = context;
            colors = mContext.getResources().getIntArray(R.array.colors);
        }

        @Override
        public int getCount() {
            return colors.length;
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
            ImageView ivColor;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                ivColor = new ImageView(mContext);
                ivColor.setLayoutParams(new GridView.LayoutParams(200, 200));
                ivColor.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ivColor.setPadding(8, 8, 8, 8);
            } else {
                ivColor = (ImageView) convertView;
            }

            ivColor.setImageResource(R.drawable.ic_circle);
            ivColor.setColorFilter(colors[position]);
            ivColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mColorListener.colorSelected(colors[position]);
                    ColorDialog.this.dismiss();
                }
            });
            return ivColor;
        }
    }
}
