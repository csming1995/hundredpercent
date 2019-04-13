package com.csming.percent.main.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.csming.percent.R;
import com.csming.percent.data.vo.Record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Created by csming on 2018/10/4.
 */
public class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int ITEM_TYPE_NORMAL = 1;
    private int ITEM_TYPE_FOOTER = 2;

    private List<Record> records;

    private OnFinishChangeListener mOnFinishChangeListener;

    public RecordListAdapter() {
        super();
    }

    public void setOnFinishChangeListener(OnFinishChangeListener onFinishChangeListener) {
        this.mOnFinishChangeListener = onFinishChangeListener;
    }

    public void setData(List<Record> records) {
        if (this.records == null) {
            this.records = new ArrayList<>();
        }
        this.records.clear();
        this.records.addAll(records);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == ITEM_TYPE_NORMAL) {
            Record record = records.get(position);
            if (record != null) {
                ((RecordNormalViewHolder) holder).setTitle(record.getTitle());
                ((RecordNormalViewHolder) holder).setDescription(record.getDescription());
                ((RecordNormalViewHolder) holder).setDate(record.getDate());
                ((RecordNormalViewHolder) holder).setFinish(record.isFinish());
                ((RecordNormalViewHolder) holder).setToggerOnClickListener(view -> {
                    ((RecordNormalViewHolder) holder).setFinish(!record.isFinish());
                    if (mOnFinishChangeListener != null) {
                        mOnFinishChangeListener.onFinishChanged(view, position - 1, record, !record.isFinish());
                    }
                });
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == ITEM_TYPE_FOOTER) {
            View view = layoutInflater.inflate(R.layout.item_list_record_footer, parent, false);
            return new FooterViewHolder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.item_list_record, parent, false);
            return new RecordNormalViewHolder(view);
        }
    }

    @Override
    public int getItemCount() {
        return this.records == null ? 1 : this.records.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return ITEM_TYPE_FOOTER;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }

    public interface OnFinishChangeListener {
        void onFinishChanged(View view, int position, Record record, boolean finish);
    }

    private static class RecordNormalViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private TextView mTvDescription;
        private ToggleButton mTbFinish;
        private TextView mTvDate;

        private RecordNormalViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvDescription = itemView.findViewById(R.id.tv_description);
            mTvDate = itemView.findViewById(R.id.tv_date);
            mTbFinish = itemView.findViewById(R.id.tb_is_finished);
        }

        private void setTitle(String title) {
            mTvTitle.setText(title);
        }

        private void setDescription(String title) {
            if (!TextUtils.isEmpty(title)) {
                mTvDescription.setVisibility(View.VISIBLE);
                mTvDescription.setText(title);
            } else {
                mTvDescription.setVisibility(View.GONE);
            }
        }

        private void setDate(long date) {
            if (date == 0) {
                mTvDate.setVisibility(View.GONE);
            } else {
                mTvDate.setVisibility(View.VISIBLE);
                mTvDate.setText(DateFormat.format("yyyy-MM-dd", new Date(date)));
            }
        }

        private void setToggerOnClickListener(View.OnClickListener onClickListener) {
            mTbFinish.setOnClickListener(onClickListener);
        }

        private void setFinish(boolean finish) {
            if (finish) {
                mTvTitle.setPaintFlags(mTvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                mTvTitle.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_9E9E9E));
                mTvDescription.setPaintFlags(mTvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                mTvDescription.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_9E9E9E));
                mTvDate.setPaintFlags(mTvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                mTvDate.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_9E9E9E));
            } else {
                mTvTitle.setPaintFlags(mTvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                mTvTitle.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_424242));
                mTvDescription.setPaintFlags(mTvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                mTvDescription.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_757575));
                mTvDate.setPaintFlags(mTvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                mTvDate.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_757575));
            }
            mTbFinish.setChecked(finish);
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class FooterViewHolder extends RecyclerView.ViewHolder {

        private FooterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
