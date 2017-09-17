package com.youknow.timeisgold.view.activity.details;

import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.utils.DateTimeUtil;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Aaron on 06/09/2017.
 */

public class StopActivityDialog extends DialogFragment {

    public interface StopActivityListener {
        void stopActivity(Activity activity);
    }

    StopActivityListener mListener;

    public void setListener(StopActivityListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Category category = getArguments().getParcelable(getString(R.string.key_category));
        final Activity activity = getArguments().getParcelable(getString(R.string.key_activity));

        View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_stop_activity, null);
        TextView tvStartTime = (TextView) rootView.findViewById(R.id.tv_start_time);
        TextView tvEndTime = (TextView) rootView.findViewById(R.id.tv_end_time);
        TextView tvElapsedTime = (TextView) rootView.findViewById(R.id.tv_elapsed_time);
        TextView tvCategoryName = (TextView) rootView.findViewById(R.id.collapsing_toolbar);

        tvStartTime.setText(getString(R.string.started_at, DateTimeUtil.DATE_TIME_FORMAT.format(activity.getStartTime())));
        tvEndTime.setText(getString(R.string.ended_at, DateTimeUtil.DATE_TIME_FORMAT.format(activity.getEndTime())));
        tvElapsedTime.setText(DateTimeUtil.getElapsedTimeShort(activity.getRelElapsedTime()));
        tvCategoryName.setText(category.getName());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView).setTitle(getString(R.string.stop_activity_confirm_message))
        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.stopActivity(activity);
            }
        });

        return builder.create();
    }

}
