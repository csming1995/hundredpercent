package com.csming.percent.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csming.percent.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Created by csming on 2018/10/4.
 */
public class RecordGroupListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int ITEM_TYPE_NORMAL = 1;
    private int ITEM_TYPE_HEADER = 2;

    private List<String> revords;

    public RecordGroupListAdapter() {
        super();
    }

    public void setData(List<String> plans) {
        if (this.revords == null) {
            this.revords = new ArrayList<>();
        }
        this.revords.clear();
        this.revords.addAll(plans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == ITEM_TYPE_HEADER) {
            View view = layoutInflater.inflate(R.layout.item_list_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.item_list_record_group, parent, false);
            return new RecordGroupNormalViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return this.revords == null ? 1 : this.revords.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_HEADER;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }

    private static class RecordGroupNormalViewHolder extends RecyclerView.ViewHolder {

        RecordGroupNormalViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
