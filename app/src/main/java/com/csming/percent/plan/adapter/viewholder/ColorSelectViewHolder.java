package com.csming.percent.plan.adapter.viewholder;

import android.view.View;
import android.widget.FrameLayout;

import com.csming.percent.R;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Created by csming on 2018/4/8.
 */

public class ColorSelectViewHolder extends RecyclerView.ViewHolder {

    private FrameLayout mFlColorContainer;

    private View mViewColor;


    public ColorSelectViewHolder(View itemView) {
        super(itemView);
        mFlColorContainer = itemView.findViewById(R.id.fl_color_container);
        mViewColor = itemView.findViewById(R.id.view_color);
    }

    public void setItemColor(@ColorRes int color) {
        mViewColor.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), color));
    }

    public void setIsSelected(boolean isSelected) {
        if (isSelected) {
            mFlColorContainer.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.background_color_selected));
        } else {
            mFlColorContainer.setBackground(null);
        }
    }

    public void setTag(int tag) {
        itemView.setTag(tag);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        itemView.setOnClickListener(onClickListener);
    }

}
