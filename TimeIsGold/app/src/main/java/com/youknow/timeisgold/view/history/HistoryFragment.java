package com.youknow.timeisgold.view.history;


import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.History;
import com.youknow.timeisgold.view.history.addedit.AddEditHistoryActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements HistoryContract.View, HistoryAdapter.HistoryListener {

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.iv_empty_info)
    ImageView mIvEmptyInfo;
    @BindView(R.id.tv_empty_info)
    TextView mTvEmptyInfo;
    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;
    HistoryAdapter mAdapter;

    private HistoryContract.Presenter mPresenter;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = HistoryPresenter.getInstance(getContext());
        mPresenter.setView(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, root);

        mAdapter = new HistoryAdapter(this);
        mRvHistory.setAdapter(mAdapter);

        mProgressBar.setVisibility(View.VISIBLE);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.history_option_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear_all:
                DeleteConfirmDialog dialog = new DeleteConfirmDialog();
                dialog.setPresenter(mPresenter);
                dialog.show(getFragmentManager(), "");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getAllHistory();
    }

    @Override
    public void onClickHistory(History history) {
        Intent intent = new Intent(getContext(), AddEditHistoryActivity.class);
        intent.putExtra(getString(R.string.key_history), history);
        startActivity(intent);
    }

    @Override
    public void showEmptyHistory() {
        mRvHistory.setVisibility(View.GONE);
        mIvEmptyInfo.setVisibility(View.VISIBLE);
        mTvEmptyInfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadedHistories(List<History> histories) {
        mProgressBar.setVisibility(View.GONE);
        if (histories.isEmpty()) {
            showEmptyHistory();
        } else {
            mAdapter.setHistoryList(histories);
        }
    }
}
