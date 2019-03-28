package com.csming.percent.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csming.percent.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Created by csming on 2018/10/4.
 */
public class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> records;

    public RecordListAdapter() {
        super();
    }

    public void setData(List<String> plans) {
        if (this.records == null) {
            this.records = new ArrayList<>();
        }
        this.records.clear();
        this.records.addAll(plans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_list_record, parent, false);
        return new RecordGroupNormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return this.records == null ? 0 : this.records.size();
    }

    private static class RecordGroupNormalViewHolder extends RecyclerView.ViewHolder {

        RecordGroupNormalViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
