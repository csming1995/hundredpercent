package com.csming.percent.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csming.percent.R;
import com.csming.percent.data.vo.Record;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Created by csming on 2018/10/4.
 */
public class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Record> records;

    private OnItemLongClickListener mOnLongClickListener;

    public RecordListAdapter() {
        super();
    }

    public void setData(List<Record> records) {
        if (this.records == null) {
            this.records = new ArrayList<>();
        }
        this.records.clear();
        this.records.addAll(records);
        notifyDataSetChanged();
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onLongClickListener) {
        this.mOnLongClickListener = onLongClickListener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position, Record record);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_list_record, parent, false);
        return new RecordNormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Record record = records.get(position);
        if (record != null) {
            ((RecordNormalViewHolder)holder).setTitle(record.getTitle());
            ((RecordNormalViewHolder)holder).setOnLongClickListener(view -> {
                if (mOnLongClickListener != null) {
                    mOnLongClickListener.onItemLongClick(view, position, record);
                }
                return true;
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.records == null ? 0 : this.records.size();
    }

    private static class RecordNormalViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;

        private RecordNormalViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
        }

        private void setTitle(String title) {
            mTvTitle.setText(title);
        }

        private void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
            itemView.setOnLongClickListener(onLongClickListener);
        }
    }
}
