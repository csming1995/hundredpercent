package com.csming.percent.record;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.csming.percent.R;
import com.csming.percent.SlideTouchEventListener;
import com.csming.percent.common.AnalyticsUtil;
import com.csming.percent.common.Contacts;
import com.csming.percent.common.LoadingFragment;
import com.csming.percent.common.widget.statuslayout.StatusLayout;
import com.csming.percent.plan.AddPlanActivity;
import com.csming.percent.record.adapter.RecordListAdapter;
import com.csming.percent.record.viewmodel.RecordsViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * @author Created by csming on 2019/3/29.
 */
public class RecordsActivity extends DaggerAppCompatActivity {

    private static final String EXTRA_TAG_PLAN_ID = "EXTRA_TAG_PLAN_ID";

    public static Intent getIntent(Context context, int planId) {
        Intent intent = new Intent(context, RecordsActivity.class);
        intent.putExtra(EXTRA_TAG_PLAN_ID, planId);
        return intent;
    }

    private StatusLayout mStatusLayout;
    private TextView mTvProgress;
    private FloatingActionButton mFabAdd;

    private Toolbar mToolbar;
    private AppBarLayout mAppBarLayout;
//    private ImageView mIvEdit;

    private RecyclerView mRvRecords;
    private LinearLayoutManager mLinearLayoutManager;
    private RecordListAdapter mAdapterRecord;

    private SlideTouchEventListener mSlideTouchEventListener;

    @Inject
    ViewModelProvider.Factory factory;

    private RecordsViewModel mRecordsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        initToolBar();
        initView();
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
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mAdapterRecord == null || !mAdapterRecord.clearDeleteState()) {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_records, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            startActivity(AddPlanActivity.getIntent(this, true, mRecordsViewModel.getPlanId()));
            overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
//            showDeleteDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        mToolbar = findViewById(R.id.toolbar);
        mAppBarLayout = findViewById(R.id.app_bar_layout);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
        }

        initToolbarColor();

    }

    private void initView() {
//        mIvEdit = findViewById(R.id.iv_setting);
        mStatusLayout = findViewById(R.id.status_layout);
        mTvProgress = findViewById(R.id.tv_progress);
        mFabAdd = findViewById(R.id.fab_add_record);
        mRvRecords = findViewById(R.id.rv_records);
        SimpleItemAnimator animator = (SimpleItemAnimator) mRvRecords.getItemAnimator();
        if (animator != null) {
            animator.setSupportsChangeAnimations(false);
        }

        mStatusLayout.setEmptyMessageView(R.string.records_empty, null, null);

        mFabAdd.setOnClickListener(v -> {
            startActivity(AddRecordActivity.getIntent(this, mRecordsViewModel.getPlanId()));
            overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
        });

        mSlideTouchEventListener = new SlideTouchEventListener() {
            @Override
            public void onTouchUp() {
            }

            @Override
            public void onTouchDown() {
                onBackPressed();
            }

            @Override
            public void onTouchLeft() {
            }

            @Override
            public void onTouchRight() {
            }
        };
        mSlideTouchEventListener.setDistance(getResources().getDimension(R.dimen.min_distance_slide));

        mAdapterRecord = new RecordListAdapter();

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRvRecords.setLayoutManager(mLinearLayoutManager);
        mRvRecords.setAdapter(mAdapterRecord);

        mAdapterRecord.setOnItemClickListener((view, position, record) -> {
            startActivity(AddRecordActivity.getIntent(this, mRecordsViewModel.getPlanId(), true, record.id, record.getTitle(), record.getDescription()));
            overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
        });

        mAdapterRecord.setOnItemDeleteClickListener((view, position, record) -> {
            LoadingFragment.show(getSupportFragmentManager());
            mRecordsViewModel.delete(record);
        });

        mAdapterRecord.setOnFinishChangeListener((view, position, record, finish) -> {
            mRecordsViewModel.updateRecordFinish(record, finish);
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mRecordsViewModel = ViewModelProviders.of(this, factory).get(RecordsViewModel.class);
        mRecordsViewModel.setPlanId(getIntent().getIntExtra(EXTRA_TAG_PLAN_ID, 0));

        mRecordsViewModel.getRecords().observe(this, records -> {
            if (records.size() > 0) {
                mAdapterRecord.setData(records);
                mStatusLayout.showNormalView();
            } else {
                mStatusLayout.showEmptyMessageView();
            }
        });

        mRecordsViewModel.getPlan().observe(this, plan -> {
            if (plan != null) {
                mTvProgress.setText(plan.getFinished() + "/" + plan.getCount());
                mToolbar.setTitle(plan.getTitle());
                mAdapterRecord.setDescription(plan.getDescription());
            }
        });

        mRecordsViewModel.getRecordState().observe(this, state -> {
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
    }

    private void initToolbarColor() {

//        float[] hsv = new float[3];
//        Color.colorToHSV(color, hsv);
//        float val = 0.8F;
//        Color.HSVToColor(new float[]{hsv[0], hsv[1], val})

//        setTransparentStatusBar(getResources().getColor(R.color.color_select_18_dark));
//        mAppBarLayout.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
//        mToolbar.setTitleTextColor(getResources().getColor(R.color.color_ffffff));
    }

    private void setTransparentStatusBar(int color) {
        //5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(color);
            //4.4到5.0
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

}
