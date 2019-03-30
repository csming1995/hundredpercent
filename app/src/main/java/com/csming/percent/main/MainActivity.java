package com.csming.percent.main;

import android.os.Bundle;

import com.csming.percent.R;
import com.csming.percent.main.adapter.PlanListAdapter;
import com.csming.percent.main.viewmodel.MainViewModel;
import com.csming.percent.plan.AddPlanActivity;
import com.csming.percent.record.RecordsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private RecyclerView mRvPlans;
    private LinearLayoutManager mLinearLayoutManager;
    private PlanListAdapter mAdapterPlans;

    private FloatingActionButton mFabAddPlan;

    @Inject
    ViewModelProvider.Factory factory;

    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 全屏显示
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        mRvPlans = findViewById(R.id.rv_plans);
        mFabAddPlan = findViewById(R.id.fab_add_plan);

        mAdapterPlans = new PlanListAdapter();

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRvPlans.setLayoutManager(mLinearLayoutManager);
        mRvPlans.setAdapter(mAdapterPlans);

        mAdapterPlans.setOnItemClickListener((view1, position, plan) -> {
            startActivity(RecordsActivity.getIntent(this, plan.getId(), plan.getTitle(), plan.getColor()));
            overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
        });

        mFabAddPlan.setOnClickListener(v -> {
//            ActivityOptionsCompat options =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
//                            mFlRoot, getString(R.string.transition_add_add_plan));
            startActivity(AddPlanActivity.getIntent(this));
            overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
        });

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mMainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);

        mMainViewModel.findAllPlans().observe(this, plans -> {
            mAdapterPlans.setData(plans);
        });
    }
}
