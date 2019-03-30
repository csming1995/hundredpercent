package com.csming.percent.plan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csming.percent.R;
import com.csming.percent.plan.adapter.viewholder.ColorSelectViewHolder;
import com.csming.percent.plan.vo.ColorEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Created by csming on 2018/4/8.
 */

public class ColorSelectAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private int selectIndex = 0;
    private List<ColorEntity> mDatas;
    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public ColorSelectAdapter(List<ColorEntity> datas) {
        setData(datas);
    }

    public void setData(List<ColorEntity> datas) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.clear();
        if (datas != null) {
            mDatas.addAll(datas);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private void setSelectIndex(int index) {
        int preIndex = selectIndex;
        selectIndex = index;
        notifyItemChanged(preIndex);
        notifyItemChanged(selectIndex);
    }

    @Override
    public void onClick(View v) {
        setSelectIndex((Integer) v.getTag());
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_color_select_list, parent, false);
        return new ColorSelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ColorEntity item = mDatas.get(position);
        if (item != null) {
            ((ColorSelectViewHolder) holder).setItemColor(item.getColorValue());
        }
        if (position == selectIndex) {
            ((ColorSelectViewHolder) holder).setIsSelected(true);
        } else {
            ((ColorSelectViewHolder) holder).setIsSelected(false);
        }

        ((ColorSelectViewHolder) holder).setTag(position);
        ((ColorSelectViewHolder) holder).setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }
}
