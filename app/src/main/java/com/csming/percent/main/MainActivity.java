package com.csming.percent.main;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.csming.percent.R;
import com.csming.percent.common.AnalyticsUtil;
import com.csming.percent.common.ApplicationConfig;
import com.csming.percent.common.Contacts;
import com.csming.percent.common.LoadingFragment;
import com.csming.percent.common.widget.statuslayout.StatusLayout;
import com.csming.percent.main.adapter.PlanListAdapter;
import com.csming.percent.main.viewmodel.MainViewModel;
import com.csming.percent.plan.AddPlanActivity;
import com.csming.percent.plan.vo.ColorEntity;
import com.csming.percent.record.RecordsActivity;
import com.csming.percent.setting.SettingActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private StatusLayout mStatusLayout;
    private RecyclerView mRvPlans;
    private LinearLayoutManager mLinearLayoutManager;
    private PlanListAdapter mAdapterPlans;

    private FloatingActionButton mFabAddPlan;

    private Toolbar toolbar;

    private AlertDialog.Builder mDeleteDialogBuilder;

    @Inject
    ViewModelProvider.Factory factory;

    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 全屏显示
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initToolBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyticsUtil.onResume(this);
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyticsUtil.onPause(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_info) {
            startActivity(SettingActivity.getIntent(this));
            overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }

    }

    private void initView() {
        mStatusLayout = findViewById(R.id.status_layout);
        mRvPlans = findViewById(R.id.rv_plans);
        mFabAddPlan = findViewById(R.id.fab_add_plan);

        SimpleItemAnimator animator = (SimpleItemAnimator) mRvPlans.getItemAnimator();
        if (animator != null) {
            animator.setSupportsChangeAnimations(false);
        }

        mAdapterPlans = new PlanListAdapter();

        mStatusLayout.setEmptyMessageView(R.string.plans_empty, null, null);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRvPlans.setLayoutManager(mLinearLayoutManager);
        mRvPlans.setAdapter(mAdapterPlans);

        mAdapterPlans.setOnItemClickListener((view1, position, plan) -> {
            startActivity(RecordsActivity.getIntent(this, plan.getId()));
            overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
        });

        mAdapterPlans.setOnItemDeleteClickListener((view, position, plan) -> {
            showDeleteDialog(plan.getId());
//            startActivity(AddPlanActivity.getIntent(this, true, plan.getId()));
//            overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
        });

        mFabAddPlan.setOnClickListener(v -> {
            startActivity(AddPlanActivity.getIntent(this));
            overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
        });

//        PopupWindow
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

        mMainViewModel.findPlans().observe(this, plans -> {
            if (plans.size() > 0) {
                mAdapterPlans.setData(plans);
                mStatusLayout.showNormalView();
            } else {
                mStatusLayout.showEmptyMessageView();
            }
        });

        mMainViewModel.getDeletePlanState().observe(this, state -> {
            switch (state) {
                case Contacts.STATE_NORMAL: {
                    LoadingFragment.hidden();
                    break;
                }
                case Contacts.STATE_SUCCESS: {
                    LoadingFragment.hidden();
                    break;
                }
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

    private void showDeleteDialog(final int planId) {
        mMainViewModel.setDeletePlan(planId);
        if (mDeleteDialogBuilder == null) {
            mDeleteDialogBuilder = new AlertDialog.Builder(this);
            mDeleteDialogBuilder.setPositiveButton(R.string.delete_sure, (dialogInterface, i) -> {
                if (mMainViewModel != null) {
                    LoadingFragment.show(getSupportFragmentManager());
                    mMainViewModel.deletePlan();
                }
            });
            mDeleteDialogBuilder.setNegativeButton(R.string.delete_cancel, (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            mDeleteDialogBuilder.setMessage(R.string.delete_dialog_message);
        }

        mDeleteDialogBuilder.show();
    }
}
