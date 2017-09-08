package com.youknow.timeisgold.view.history;


import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.History;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements HistoryContract.View, HistoryAdapter.HistoryListener {

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, root);

        mAdapter = new HistoryAdapter(this);
        mRvHistory.setAdapter(mAdapter);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        List<History> historyList = mPresenter.getAllHistory();
        mAdapter.setHistoryList(historyList);
    }

    @Override
    public void onClickHistory(History history) {

    }
}
