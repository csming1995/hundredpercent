package com.csming.percent.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.csming.percent.R;
import com.csming.percent.common.ApplicationConfig;
import com.csming.percent.common.widget.sliderecyclerview.SlideRecyclerView;
import com.csming.percent.common.widget.statuslayout.StatusLayout;
import com.csming.percent.main.adapter.PlanListAdapter;
import com.csming.percent.main.viewmodel.MainViewModel;
import com.csming.percent.plan.AddPlanActivity;
import com.csming.percent.plan.vo.ColorEntity;
import com.csming.percent.record.RecordsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private StatusLayout mStatusLayout;
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
        mStatusLayout = findViewById(R.id.status_layout);
        mRvPlans = findViewById(R.id.rv_plans);
        mFabAddPlan = findViewById(R.id.fab_add_plan);

        mAdapterPlans = new PlanListAdapter();

        mStatusLayout.setEmptyMessageView(R.string.plans_empty, null, null);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRvPlans.setLayoutManager(mLinearLayoutManager);
        mRvPlans.setAdapter(mAdapterPlans);

        mAdapterPlans.setOnItemClickListener((view1, position, plan) -> {
            startActivity(RecordsActivity.getIntent(this, plan.getId(), plan.getTitle(), plan.getColor()));
            overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
        });

        mFabAddPlan.setOnClickListener(v -> {
            startActivity(AddPlanActivity.getIntent(this));
            overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
        });

//        View contentView = LayoutInflater.from(this).inflate(R.layout.popup_delete, null);
//        mFlPopupDelete = contentView.findViewById(R.id.fl_delete);
//        mPopupWindowDelete = new PopupWindow(contentView);
//        mPopupWindowDelete.setContentView(contentView);
//        mPopupWindowDelete.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//        mPopupWindowDelete.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        mPopupWindowDelete.setOutsideTouchable(true);
//        mAdapterPlans.setOnItemLongClickListener((view, position, record) -> {
//            int offsetX = Math.abs(view.getWidth()) / 2;
//            int offsetY = -view.getHeight();
//            PopupWindowCompat.showAsDropDown(mPopupWindowDelete, view, offsetX, offsetY, Gravity.START);
//            mFlPopupDelete.setOnClickListener(v -> {
////                mRecordsViewModel.delete(record);
//                mPopupWindowDelete.dismiss();
//            });
//        });
//        mFlPopupDelete.setOnClickListener(view -> {
//            Timber.d("Deleteaaaa");
//        });

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mMainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);

        mMainViewModel.findAllPlans().observe(this, plans -> {
            if (plans.size() > 0) {
                mAdapterPlans.setData(plans);
                mStatusLayout.showNormalView();
            } else {
                mStatusLayout.showEmptyMessageView();
            }
        });


        if (ApplicationConfig.getIsFirstIn(this)) {
            mMainViewModel.initPlan(
                    getResources().getString(R.string.first_plan_title),
                    "",
                    getResources().getColor(ColorEntity.COLOR_VALUES[0])
            );
            ApplicationConfig.setIsFirstIn(this);
        }
    }
}
