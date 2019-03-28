package com.csming.percent.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csming.percent.R;
import com.csming.percent.main.adapter.RecordGroupListAdapter;
import com.csming.percent.main.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerFragment;

/**
 * @author Created by csming on 2018/11/21.
 */
public class RecordFragment extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory factory;

    private MainViewModel mainViewModel;

    private RecyclerView mRvPlans;
    private LinearLayoutManager mLinearLayoutManager;
    private RecordGroupListAdapter mAdapterRecordGroup;

    private List<String> plans;

    public static RecordFragment getInstance() {
        return new RecordFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_records, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initView(View view) {
        mRvPlans = view.findViewById(R.id.rv_records);
        mAdapterRecordGroup = new RecordGroupListAdapter();

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRvPlans.setLayoutManager(mLinearLayoutManager);
        mRvPlans.setAdapter(mAdapterRecordGroup);

        plans = new ArrayList<>(3);
        plans.add("TODO");
        plans.add("Test");
        plans.add("啊啊啊啊");
        plans.add("啊啊啊啊");
        plans.add("啊啊啊啊");
        plans.add("啊啊啊啊");
        plans.add("啊啊啊啊");
        plans.add("啊啊啊啊");
        plans.add("啊啊啊啊");
        plans.add("啊啊啊啊");
        plans.add("啊啊啊啊");
        plans.add("啊啊啊啊");
        plans.add("啊啊啊啊");
        plans.add("啊啊啊啊");

        mAdapterRecordGroup.setData(plans);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mainViewModel = ViewModelProviders.of(getActivity(), factory).get(MainViewModel.class);
    }
}
