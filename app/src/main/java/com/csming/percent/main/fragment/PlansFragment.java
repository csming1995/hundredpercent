package com.csming.percent.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csming.percent.R;
import com.csming.percent.main.adapter.PlanListAdapter;
import com.csming.percent.main.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerFragment;

/**
 * @author Created by csming on 2018/11/20.
 */
public class PlansFragment extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory factory;

    private MainViewModel mMainViewModel;

    private RecyclerView mRvPlans;
    private LinearLayoutManager mLinearLayoutManager;
    private PlanListAdapter mAdapterPlans;

    private List<String> plans;

    public static Fragment getInstance() {
        return new PlansFragment();
    }

    public PlansFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plans, container, false);
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
        mRvPlans = view.findViewById(R.id.rv_plans);
        mAdapterPlans = new PlanListAdapter();

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRvPlans.setLayoutManager(mLinearLayoutManager);
        mRvPlans.setAdapter(mAdapterPlans);

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

        mAdapterPlans.setData(plans);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mMainViewModel = ViewModelProviders.of(getActivity(), factory).get(MainViewModel.class);
    }
}
