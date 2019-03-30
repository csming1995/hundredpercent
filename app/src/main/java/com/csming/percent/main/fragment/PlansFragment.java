package com.csming.percent.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.csming.percent.R;
import com.csming.percent.data.vo.Plan;
import com.csming.percent.main.adapter.PlanListAdapter;
import com.csming.percent.main.viewmodel.MainViewModel;
import com.csming.percent.plan.AddPlanActivity;
import com.csming.percent.record.RecordsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerFragment;

/**
 * @author Created by csming on 2018/11/20.
 */
public class PlansFragment extends DaggerFragment {

    private FrameLayout mFlRoot;
    private RecyclerView mRvPlans;
    private LinearLayoutManager mLinearLayoutManager;
    private PlanListAdapter mAdapterPlans;

    private FloatingActionButton mFabAddPlan;

    @Inject
    ViewModelProvider.Factory factory;

    private MainViewModel mMainViewModel;

    private List<Plan> plans;

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
        mFlRoot = view.findViewById(R.id.fl_root);
        mRvPlans = view.findViewById(R.id.rv_plans);
        mFabAddPlan = view.findViewById(R.id.fab_add_plan);

        mAdapterPlans = new PlanListAdapter();

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRvPlans.setLayoutManager(mLinearLayoutManager);
        mRvPlans.setAdapter(mAdapterPlans);

        mAdapterPlans.setOnItemClickListener((view1, position, plan) -> {
            startActivity(RecordsActivity.getIntent(getActivity()));
        });

        mFabAddPlan.setOnClickListener(v -> {
//            ActivityOptionsCompat options =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
//                            mFlRoot, getString(R.string.transition_add_add_plan));
            startActivity(AddPlanActivity.getIntent(getActivity()));
            getActivity().overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
        });

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mMainViewModel = ViewModelProviders.of(getActivity(), factory).get(MainViewModel.class);

        mMainViewModel.findAllPlans().observe(getActivity(), plans -> {
            mAdapterPlans.setData(plans);
        });
    }
}
