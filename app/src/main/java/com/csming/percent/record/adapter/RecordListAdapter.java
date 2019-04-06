package com.csming.percent.record.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.csming.percent.R;
import com.csming.percent.data.vo.Record;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Created by csming on 2018/10/4.
 */
public class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int ITEM_TYPE_NORMAL = 1;
    private int ITEM_TYPE_HEADER = 2;
    private int ITEM_TYPE_FOOTER = 3;

    private List<Record> records;

    private OnItemClickListener mOnItemClickListener;
    private OnItemDeleteClickListener mOnItemDeleteClickListener;
    private OnFinishChangeListener mOnFinishChangeListener;

    private int deleteIndex = -1;

    private String description = "";

    public interface OnItemDeleteClickListener {
        void onItemDeleteClick(View view, int position, Record record);
    }

    public interface OnFinishChangeListener {
        void onFinishChanged(View view, int position, Record record, boolean finish);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemDeleteClickListener(OnItemDeleteClickListener onLongClickListener) {
        this.mOnItemDeleteClickListener = onLongClickListener;
    }

    public void setOnFinishChangeListener(OnFinishChangeListener onFinishChangeListener) {
        this.mOnFinishChangeListener = onFinishChangeListener;
    }

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

    public void setDescription(String description) {
        this.description = description;
        notifyItemChanged(0);
    }

    public boolean clearDeleteState() {
        if (deleteIndex == -1) return false;
        int pre = deleteIndex;
        deleteIndex = -1;
        notifyItemChanged(pre);
        deleteIndex = -1;
        return true;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == ITEM_TYPE_HEADER) {
            ((HeaderViewHolder) holder).setDescription(description);
        } else if (getItemViewType(position) == ITEM_TYPE_NORMAL) {
            Record record = records.get(position - 1);
            if (record != null) {
                ((RecordNormalViewHolder) holder).setTitle(record.getTitle());
                ((RecordNormalViewHolder) holder).setDescription(record.getDescription());
                ((RecordNormalViewHolder) holder).setFinish(record.isFinish());
                ((RecordNormalViewHolder) holder).setOnClickListener(view -> {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position - 1, record);
                    }
                });
                ((RecordNormalViewHolder) holder).setToggerOnClickListener(view -> {
                    ((RecordNormalViewHolder) holder).setFinish(!record.isFinish());
                    if (mOnFinishChangeListener != null) {
                        mOnFinishChangeListener.onFinishChanged(view, position - 1, record, !record.isFinish());
                    }
                });
                ((RecordNormalViewHolder) holder).setOnDeleteClickListener(view -> {
                    deleteIndex = -1;
                    if (mOnItemDeleteClickListener != null) {
                        mOnItemDeleteClickListener.onItemDeleteClick(view, position - 1, record);
                    }
                });
                ((RecordNormalViewHolder) holder).setDeleteVisibity(position == deleteIndex);
                ((RecordNormalViewHolder) holder).setLongClickListener(view -> {
                    if (deleteIndex == position) {
                        deleteIndex = -1;
                        notifyItemChanged(position);
                    } else {
                        int pre = deleteIndex;
                        notifyItemChanged(pre);
                        deleteIndex = position;
                        notifyItemChanged(deleteIndex);
                    }
                    return true;
                });
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == ITEM_TYPE_HEADER) {
            View view = layoutInflater.inflate(R.layout.item_list_record_header, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == ITEM_TYPE_FOOTER) {
            View view = layoutInflater.inflate(R.layout.item_list_record_footer, parent, false);
            return new FooterViewHolder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.item_list_record, parent, false);
            return new RecordNormalViewHolder(view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Record record);
    }

    @Override
    public int getItemCount() {
        return this.records == null ? 2 : this.records.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_HEADER;
        } else if (position == getItemCount() - 1) {
            return ITEM_TYPE_FOOTER;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }

    private static class RecordNormalViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private TextView mTvDescription;
        private ToggleButton mTbFinish;
        private ImageView mIvDelete;

        private RecordNormalViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvDescription = itemView.findViewById(R.id.tv_description);
            mTbFinish = itemView.findViewById(R.id.tb_is_finished);
            mIvDelete = itemView.findViewById(R.id.iv_delete);
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

        private void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }

        private void setLongClickListener(View.OnLongClickListener onLongClickListener) {
            itemView.setOnLongClickListener(onLongClickListener);
        }

        private void setOnDeleteClickListener(View.OnClickListener onClickListener) {
            mIvDelete.setOnClickListener(onClickListener);
        }

        private void setToggerOnClickListener(View.OnClickListener onClickListener) {
            mTbFinish.setOnClickListener(onClickListener);
        }

        private void setDeleteVisibity(boolean isShow) {
            mTbFinish.setVisibility(isShow ? View.GONE : View.VISIBLE);
            mIvDelete.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }

        private void setFinish(boolean finish) {
            if (finish) {
                mTvTitle.setPaintFlags(mTvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                mTvTitle.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_9E9E9E));
                mTvDescription.setPaintFlags(mTvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                mTvDescription.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_9E9E9E));
            } else {
                mTvTitle.setPaintFlags(mTvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                mTvTitle.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_424242));
                mTvDescription.setPaintFlags(mTvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                mTvDescription.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_757575));
            }
            mTbFinish.setChecked(finish);
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvDescription;

        private HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvDescription = itemView.findViewById(R.id.tv_description);
        }

        private void setDescription(String title) {
            mTvDescription.setText(title);
        }
    }

    private static class FooterViewHolder extends RecyclerView.ViewHolder {

        private FooterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
