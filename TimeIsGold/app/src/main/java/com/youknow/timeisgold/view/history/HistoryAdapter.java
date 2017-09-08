package com.youknow.timeisgold.view.history;

import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.History;
import com.youknow.timeisgold.utils.DateTimeUtil;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 09/09/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    List<History> mHistoryList = new ArrayList<>();
    HistoryListener mListener;

    public HistoryAdapter(HistoryListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        History history = mHistoryList.get(position);
        holder.itemContainer.setBackgroundColor(history.getColor());
        holder.ivCategoryIcon.setImageResource(history.getIcon());
        holder.tvCategoryType.setText(history.getType().name());
        holder.tvCategoryName.setText(history.getName());
        holder.tvElapsedTime.setText(DateTimeUtil.getElapsedTimeShort(history.getElapsedTime()));
        holder.tvTime.setText(DateTimeUtil.getTime(history.getStartTime(), history.getEndTime()));
    }

    @Override
    public int getItemCount() {
        return mHistoryList.size();
    }

    public void setHistoryList(List<History> historyList) {
        mHistoryList.clear();
        mHistoryList.addAll(historyList);
        notifyDataSetChanged();
    }

    interface HistoryListener {
        void onClickHistory(History history);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        History history;
        ConstraintLayout itemContainer;
        ImageView ivCategoryIcon;
        TextView tvCategoryType;
        TextView tvCategoryName;
        TextView tvElapsedTime;
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            itemContainer = (ConstraintLayout) itemView.findViewById(R.id.item_container);
            ivCategoryIcon = (ImageView) itemView.findViewById(R.id.iv_category_icon);
            tvCategoryType = (TextView) itemView.findViewById(R.id.tv_category_type);
            tvCategoryName = (TextView) itemView.findViewById(R.id.tv_category_name);
            tvElapsedTime = (TextView) itemView.findViewById(R.id.tv_elapsed_time);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClickHistory(history);
                }
            });
        }
    }
}
