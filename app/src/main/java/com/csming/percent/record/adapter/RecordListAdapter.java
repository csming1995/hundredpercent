package com.csming.percent.record.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
    private OnSettingClickListener mOnSettingClickListener;

    private String description = "";

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

    public void setOnSettingClickListener(OnSettingClickListener onSettingClickListener) {
        this.mOnSettingClickListener = onSettingClickListener;
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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == ITEM_TYPE_HEADER) {
            ((HeaderViewHolder) holder).setDescription(description);
            ((HeaderViewHolder) holder).setOnClickListener(view -> {
                if (mOnSettingClickListener != null) {
                    mOnSettingClickListener.onSettingClick(view);
                }
            });
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
                ((RecordNormalViewHolder) holder).setOnDeleteClickListener(view -> {
                    if (mOnItemDeleteClickListener != null) {
                        mOnItemDeleteClickListener.onItemDeleteClick(view, position - 1, record);
                    }
                });
                ((RecordNormalViewHolder) holder).setToggerOnClickListener(view -> {
                    ((RecordNormalViewHolder) holder).setFinish(!record.isFinish());
                    if (mOnFinishChangeListener != null) {
                        mOnFinishChangeListener.onFinishChanged(view, position - 1, record, !record.isFinish());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.records == null ? 2 : this.records.size() + 2;
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

    public interface OnItemDeleteClickListener {
        void onItemDeleteClick(View view, int position, Record record);
    }

    public interface OnFinishChangeListener {
        void onFinishChanged(View view, int position, Record record, boolean finish);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Record record);
    }

    public interface OnSettingClickListener {
        void onSettingClick(View view);
    }

    private static class RecordNormalViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private TextView mTvDescription;
        private ToggleButton mTbFinish;
        private FrameLayout mFlDelete;

        private RecordNormalViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvDescription = itemView.findViewById(R.id.tv_description);
            mTbFinish = itemView.findViewById(R.id.tb_is_finished);
            mFlDelete = itemView.findViewById(R.id.fl_delete);
        }

        private void setTitle(String title) {
            mTvTitle.setText(title);
        }

        private void setDescription(String title) {
            if (TextUtils.isEmpty(title)) {
                mTvDescription.setVisibility(View.VISIBLE);
                mTvDescription.setText(title);
            } else {
                mTvDescription.setVisibility(View.GONE);
            }
//            }
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
                mTvTitle.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_424242));
            } else {
                mTvTitle.setPaintFlags(mTvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                mTvTitle.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_424242));
            }
            mTbFinish.setChecked(finish);
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvSetting;
        private TextView mTvDescription;

        private HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            mIvSetting = itemView.findViewById(R.id.iv_setting);
            mTvDescription = itemView.findViewById(R.id.tv_description);
        }

        private void setDescription(String title) {
            mTvDescription.setText(title);
        }

        private void setOnClickListener(View.OnClickListener onClickListener) {
            mIvSetting.setOnClickListener(onClickListener);
        }
    }

    private static class FooterViewHolder extends RecyclerView.ViewHolder {

        private FooterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
