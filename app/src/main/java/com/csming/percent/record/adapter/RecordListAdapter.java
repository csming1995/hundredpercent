package com.csming.percent.record.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

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

    private OnItemClickListener mOnItemClickListener;
    private OnItemDeleteClickListener mOnItemDeleteClickListener;
    private OnFinishChangeListener mOnFinishChangeListener;

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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Record record);
    }

    public void setOnItemDeleteClickListener(OnItemDeleteClickListener onLongClickListener) {
        this.mOnItemDeleteClickListener = onLongClickListener;
    }

    public interface OnItemDeleteClickListener {
        void onItemDeleteClick(View view, int position, Record record);
    }

    public void setOnFinishChangeListener(OnFinishChangeListener onFinishChangeListener) {
        this.mOnFinishChangeListener = onFinishChangeListener;
    }

    public interface OnFinishChangeListener {
        void onFinishChanged(View view, int position, Record record, boolean finish);
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
            ((RecordNormalViewHolder)holder).setFinish(record.isFinish());
            ((RecordNormalViewHolder)holder).setOnClickListener(view -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, position, record);
                }
            });
            ((RecordNormalViewHolder)holder).setOnDeleteClickListener(view -> {
                if (mOnItemDeleteClickListener != null) {
                    mOnItemDeleteClickListener.onItemDeleteClick(view, position, record);
                }
            });
            ((RecordNormalViewHolder)holder).setToggerOnClickListener(view -> {
                ((RecordNormalViewHolder)holder).setFinish(!record.isFinish());
                if (mOnFinishChangeListener != null) {
                    mOnFinishChangeListener.onFinishChanged(view, position, record, !record.isFinish());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.records == null ? 0 : this.records.size();
    }

    private static class RecordNormalViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private ToggleButton mTbFinish;
        private FrameLayout mFlDelete;

        private RecordNormalViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTbFinish = itemView.findViewById(R.id.tb_is_finished);
            mFlDelete = itemView.findViewById(R.id.fl_delete);
        }

        private void setTitle(String title) {
            mTvTitle.setText(title);
        }

        private void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }

        private void setOnDeleteClickListener(View.OnClickListener onClickListener) {
            mFlDelete.setOnClickListener(onClickListener);
        }

        private void setToggerOnClickListener(View.OnClickListener onClickListener) {
            mTbFinish.setOnClickListener(onClickListener);
        }

        private void setFinish(boolean finish) {
            if (finish) {
                mTvTitle.setPaintFlags(mTvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                mTvTitle.setPaintFlags(mTvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
            mTbFinish.setChecked(finish);
        }
    }
}
