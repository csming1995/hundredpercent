package com.csming.percent.record;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.csming.percent.R;
import com.csming.percent.SlideTouchEventListener;
import com.csming.percent.data.vo.Record;
import com.csming.percent.main.adapter.RecordListAdapter;
import com.csming.percent.main.viewmodel.MainViewModel;
import com.csming.percent.record.viewmodel.RecordsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * @author Created by csming on 2019/3/29.
 */
public class RecordsActivity extends DaggerAppCompatActivity {

    private static final String EXTRA_TAG_PLAN_ID = "EXTRA_TAG_PLAN_ID";
    private static final String EXTRA_TAG_PLAN_TITLE = "EXTRA_TAG_PLAN_TITLE";
    private static final String EXTRA_TAG_PLAN_COLOR = "EXTRA_TAG_PLAN_COLOR";

    public static Intent getIntent(Context context, int planId, String planTitle, int color) {
        Intent intent = new Intent(context, RecordsActivity.class);
        intent.putExtra(EXTRA_TAG_PLAN_ID, planId);
        intent.putExtra(EXTRA_TAG_PLAN_TITLE, planTitle);
        intent.putExtra(EXTRA_TAG_PLAN_COLOR, color);
        return intent;
    }

    private CardView mCvTitle;
    private LinearLayout mLlRoot;
    private TextView mTvTitle;
    private FloatingActionButton mFabAdd;

    private RecyclerView mRvRecords;
    private LinearLayoutManager mLinearLayoutManager;
    private RecordListAdapter mAdapterRecord;

    private SlideTouchEventListener mSlideTouchEventListener;

    private ObjectAnimator mObjectAnimatorCardPanelEnter;
    private ObjectAnimator mObjectAnimatorFabEnter;

//    private List<Record> records;

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
        initData();
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
        mCvTitle = findViewById(R.id.cv_title);
        mLlRoot = findViewById(R.id.ll_root);
        mTvTitle = findViewById(R.id.tv_title);
        mFabAdd = findViewById(R.id.fab_add_record);
        mRvRecords = findViewById(R.id.rv_records);

        mLlRoot.post(() -> {
            initAnimator();

            mObjectAnimatorCardPanelEnter.start();
            mObjectAnimatorFabEnter.start();
        });

        mFabAdd.setOnClickListener(v -> {
            startActivity(AddRecordActivity.getIntent(this, mRecordsViewModel.getPlanId()));
            overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
        });

        mSlideTouchEventListener = new SlideTouchEventListener() {
            @Override
            public void onTouchUp() {
                onBackPressed();
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

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mRecordsViewModel = ViewModelProviders.of(this, factory).get(RecordsViewModel.class);
        mRecordsViewModel.setPlanId(getIntent().getIntExtra(EXTRA_TAG_PLAN_ID, 0));
        mRecordsViewModel.setPlanTitle(getIntent().getStringExtra(EXTRA_TAG_PLAN_TITLE));

        mRecordsViewModel.getRecords().observe(this, records -> {
            mAdapterRecord.setData(records);
        });

        mCvTitle.setCardBackgroundColor(getIntent().getIntExtra(EXTRA_TAG_PLAN_COLOR, getResources().getColor(R.color.color_111111)));
        mTvTitle.setText(mRecordsViewModel.getPlanTitle());
    }
}
