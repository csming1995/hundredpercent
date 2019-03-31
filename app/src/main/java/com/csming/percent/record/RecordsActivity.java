package com.csming.percent.record;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csming.percent.R;
import com.csming.percent.SlideTouchEventListener;
import com.csming.percent.common.AnalyticsUtil;
import com.csming.percent.common.widget.sliderecyclerview.SlideRecyclerView;
import com.csming.percent.common.widget.statuslayout.StatusLayout;
import com.csming.percent.plan.AddPlanActivity;
import com.csming.percent.record.adapter.RecordListAdapter;
import com.csming.percent.record.viewmodel.RecordsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    private CardView mCvTitle;
    private CardView mCvDelete;
    private LinearLayout mLlRoot;
    private TextView mTvTitle;
    private TextView mTvProgress;
    private TextView mTvDescription;
    private FloatingActionButton mFabAdd;

    private SlideRecyclerView mRvRecords;
    private LinearLayoutManager mLinearLayoutManager;
    private RecordListAdapter mAdapterRecord;

    private SlideTouchEventListener mSlideTouchEventListener;

    private ObjectAnimator mObjectAnimatorCardPanelEnter;
    private ObjectAnimator mObjectAnimatorFabEnter;

    private AlertDialog.Builder mDeleteDialogBuilder;

    @Inject
    ViewModelProvider.Factory factory;

    private RecordsViewModel mRecordsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

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
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mSlideTouchEventListener != null) {
            mSlideTouchEventListener.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    private void initAnimator() {

        // 获取 主面板高度
        int heightCardPanel = mLlRoot.getMeasuredHeight();
        // 获取屏幕信息
        mObjectAnimatorCardPanelEnter = ObjectAnimator.ofFloat(mLlRoot, "translationY", heightCardPanel, -50, 0);
        mObjectAnimatorCardPanelEnter.setDuration(500);

        mObjectAnimatorFabEnter = ObjectAnimator.ofFloat(mFabAdd, "translationY", 500, -50, 0);
        mObjectAnimatorFabEnter.setDuration(600);
    }

    private void initView() {
        mStatusLayout = findViewById(R.id.status_layout);
        mCvTitle = findViewById(R.id.cv_title);
        mCvDelete = findViewById(R.id.cv_delete);
        mLlRoot = findViewById(R.id.ll_root);
        mTvTitle = findViewById(R.id.tv_title);
        mTvProgress = findViewById(R.id.tv_progress);
        mTvDescription = findViewById(R.id.tv_description);
        mFabAdd = findViewById(R.id.fab_add_record);
        mRvRecords = findViewById(R.id.rv_records);

        mStatusLayout.setEmptyMessageView(R.string.records_empty, null, null);

        mLlRoot.post(() -> {
            initAnimator();

            mObjectAnimatorCardPanelEnter.start();
            mObjectAnimatorFabEnter.start();
        });

        mCvTitle.setOnClickListener(view -> {
            startActivity(AddPlanActivity.getIntent(this, true, mRecordsViewModel.getPlanId()));
            overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
        });

        mCvDelete.setOnClickListener(view -> {
            showDeleteDialog();
        });

        mFabAdd.setOnClickListener(v -> {
            startActivity(AddRecordActivity.getIntent(this, mRecordsViewModel.getPlanId()));
            overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
            mRvRecords.closeMenu();
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
            mRecordsViewModel.delete(record);
            mRvRecords.closeMenu();
        });

        mAdapterRecord.setOnFinishChangeListener((view, position, record, finish) -> {
            mRvRecords.closeMenu();
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
                if (TextUtils.isEmpty(plan.getDescription())){
                    mTvDescription.setVisibility(View.GONE);
                } else {
                    mTvDescription.setVisibility(View.VISIBLE);
                }
                mTvDescription.setText(plan.getDescription());
                mTvTitle.setText(plan.getTitle());
                mCvTitle.setCardBackgroundColor(plan.getColor());
            }
        });
    }

    private void showDeleteDialog() {
        if (mDeleteDialogBuilder == null) {
            mDeleteDialogBuilder = new AlertDialog.Builder(this);
            mDeleteDialogBuilder.setPositiveButton(R.string.delete_sure, (dialogInterface, i) -> {
                if (mRecordsViewModel != null) {
                    mRecordsViewModel.deletePlan();
                    onBackPressed();
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
