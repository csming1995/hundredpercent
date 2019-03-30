package com.csming.percent.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import com.csming.percent.R;
import com.csming.percent.data.vo.Plan;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Created by csming on 2018/10/4.
 */
public class PlanListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int ITEM_TYPE_NORMAL = 1;
    private int ITEM_TYPE_HEADER = 2;

    private List<Plan> plans;

    public PlanListAdapter() {
        super();
    }

    public void setData(List<Plan> plans) {
        if (this.plans == null) {
            this.plans = new ArrayList<>();
        }
        this.plans.clear();
        this.plans.addAll(plans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == ITEM_TYPE_HEADER) {
            View view = layoutInflater.inflate(R.layout.item_list_plan_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.item_list_plan, parent, false);
            return new PlanNormalViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == ITEM_TYPE_NORMAL) {
            Plan plan = plans.get(position - 1);
            if (plan != null) {
                ((PlanNormalViewHolder)holder).setBackground(plan.getColor());
                ((PlanNormalViewHolder)holder).setTitle(plan.getTitle());
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.plans == null ? 1 : this.plans.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_HEADER;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }

    private static class PlanNormalViewHolder extends RecyclerView.ViewHolder {

        private CardView mCvItem;
        private TextView mTvTitle;

        PlanNormalViewHolder(@NonNull View itemView) {
            super(itemView);
            mCvItem = itemView.findViewById(R.id.cv_item);
            mTvTitle = itemView.findViewById(R.id.tv_title);
        }

        public void setBackground(int color) {
            mCvItem.setCardBackgroundColor(color);
        }

        public void setTitle(String title) {
            mTvTitle.setText(title);
        }

    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
