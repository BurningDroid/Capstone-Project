package com.youknow.timeisgold.view.history;

import com.youknow.timeisgold.R;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Aaron on 06/09/2017.
 */

public class DeleteConfirmDialog extends DialogFragment {

    HistoryContract.Presenter mPresenter;

    public void setPresenter(HistoryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.delete_activity_confirm_message))
        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.clearHistory();
            }
        });

        return builder.create();
    }

}
