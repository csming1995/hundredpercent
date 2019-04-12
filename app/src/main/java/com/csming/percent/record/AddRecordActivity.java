package com.csming.percent.record;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.csming.percent.R;
import com.csming.percent.SlideTouchEventListener;
import com.csming.percent.common.AnalyticsUtil;
import com.csming.percent.common.Contacts;
import com.csming.percent.common.DatePickerActivity;
import com.csming.percent.common.LoadingFragment;
import com.csming.percent.record.viewmodel.AddRecordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * @author Created by csming on 2019/3/29.
 */
public class AddRecordActivity extends DaggerAppCompatActivity {

    private static final String EXTRA_TAG_PLAN_ID = "EXTRA_TAG_PLAN_ID";

    private static final String EXTRA_TAG_IS_EDIT = "EXTRA_TAG_IS_EDIT";
    private static final String EXTRA_TAG_RECORD_ID = "EXTRA_TAG_RECORD_ID";
    private static final String EXTRA_TAG_TITLE = "EXTRA_TAG_TITLE";
    private static final String EXTRA_TAG_DESCRIPTION = "EXTRA_TAG_IS_DESCRIPTION";
    private static final String EXTRA_TAG_DATE = "EXTRA_TAG_DATE";

    public static Intent getIntent(Context context, int planId) {
        Intent intent = new Intent(context, AddRecordActivity.class);
        intent.putExtra(EXTRA_TAG_PLAN_ID, planId);
        return intent;
    }

    public static Intent getIntent(Context context, int planId, boolean isEdit, int recordId, String title, String description, long date) {
        Intent intent = new Intent(context, AddRecordActivity.class);
        intent.putExtra(EXTRA_TAG_PLAN_ID, planId);
        intent.putExtra(EXTRA_TAG_IS_EDIT, isEdit);
        intent.putExtra(EXTRA_TAG_RECORD_ID, recordId);
        intent.putExtra(EXTRA_TAG_TITLE, title);
        intent.putExtra(EXTRA_TAG_DESCRIPTION, description);
        intent.putExtra(EXTRA_TAG_DATE, date);
        return intent;
    }

    private LinearLayout mLlRoot;
    private TextView mTvTitle;
    private FloatingActionButton mFabAdd;

    private ImageView mIvClock;

    private EditText mEtTitle;
    private EditText mEtDescription;
    private TextView mTvDate;
    private Date mDate = null;

    private Toolbar toolbar;

    private ObjectAnimator mObjectAnimatorCardPanelEnter;
    private ObjectAnimator mObjectAnimatorFabEnter;

    private SlideTouchEventListener mSlideTouchEventListener;

    @Inject
    ViewModelProvider.Factory factory;

    private AddRecordViewModel mAddRecordViewModel;

    private boolean isEdit = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        initView();
        initData();
        initToolBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyticsUtil.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyticsUtil.onPause(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Contacts.RESULT_TAG_DATEPICKER) {
            if (resultCode == RESULT_OK && data != null) {
                long date = data.getLongExtra(DatePickerActivity.TAG_RESULT_DATE, 0);
                setDate(date);
            }
        }
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
        finish();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_add_record, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_post) {
//            LoadingFragment.show(getSupportFragmentManager());
//            if (isEdit) {
//                mAddRecordViewModel.updateRecord(mEtTitle.getText().toString(), mEtDescription.getText().toString());
//            } else {
//                mAddRecordViewModel.postRecord(mEtTitle.getText().toString(), mEtDescription.getText().toString());
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mSlideTouchEventListener != null) {
            mSlideTouchEventListener.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
//        toolbar = findViewById(R.id.toolbar);
////        toolbar.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
//
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowHomeEnabled(false);
//            actionBar.setTitle(R.string.title_setting);
//        }
//
//        toolbar.setTitle(isEdit ? R.string.title_edit_record : R.string.title_add_record);

    }

    private void initAnimator() {

        // 获取 主面板高度
        int heightCardPanel = mLlRoot.getMeasuredHeight();
//         获取屏幕信息
        mObjectAnimatorCardPanelEnter = ObjectAnimator.ofFloat(mLlRoot, "translationY", heightCardPanel, -50, 0);
        mObjectAnimatorCardPanelEnter.setDuration(500);

        mObjectAnimatorFabEnter = ObjectAnimator.ofFloat(mFabAdd, "translationY", 500, -50, 0);
        mObjectAnimatorFabEnter.setDuration(600);
    }

    private void initView() {
        mLlRoot = findViewById(R.id.ll_root);
        mTvTitle = findViewById(R.id.tv_title);
        mTvDate = findViewById(R.id.tv_date);

        mFabAdd = findViewById(R.id.fab_add);
        mIvClock = findViewById(R.id.iv_clock);

        mEtTitle = findViewById(R.id.et_title);
        mEtDescription = findViewById(R.id.et_description);

        mLlRoot.post(() -> {
            initAnimator();

            mObjectAnimatorCardPanelEnter.start();
            mObjectAnimatorFabEnter.start();
        });

        mFabAdd.setOnClickListener(v -> {
            LoadingFragment.show(getSupportFragmentManager());

            long date = 0;
            if (mDate != null) {
                date = mDate.getTime();
            }

            if (isEdit) {
                mAddRecordViewModel.updateRecord(mEtTitle.getText().toString(), mEtDescription.getText().toString(), date);
            } else {
                mAddRecordViewModel.postRecord(mEtTitle.getText().toString(), mEtDescription.getText().toString(), date);
            }
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

        mIvClock.setOnClickListener(view -> {
            gotoSetDate();
        });

        mTvDate.setOnClickListener(view -> {
            gotoSetDate();
        });

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mAddRecordViewModel = ViewModelProviders.of(this, factory).get(AddRecordViewModel.class);
        mAddRecordViewModel.setPlanId(getIntent().getIntExtra(EXTRA_TAG_PLAN_ID, 0));

        isEdit = getIntent().getBooleanExtra(EXTRA_TAG_IS_EDIT, false);
        if (isEdit) {
            mAddRecordViewModel.setRecordId(getIntent().getIntExtra(EXTRA_TAG_RECORD_ID, 0));
            String title = getIntent().getStringExtra(EXTRA_TAG_TITLE);
            String description = getIntent().getStringExtra(EXTRA_TAG_DESCRIPTION);
            long date = getIntent().getLongExtra(EXTRA_TAG_DATE, 0);
            mEtTitle.setText(title);
            mEtDescription.setText(description);
            setDate(date);
            mAddRecordViewModel.getPostState().observe(this, result -> {
                switch (result) {
                    case Contacts.STATE_SUCCESS: {
                        LoadingFragment.hidden();
                        Toast.makeText(this, R.string.update_record_result_success, Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        break;
                    }
                    case Contacts.STATE_UPDATE_TITLE_NULL: {
                        LoadingFragment.hidden();
                        Toast.makeText(this, R.string.post_record_result_title_null, Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            });
        } else {
            mAddRecordViewModel.getPostState().observe(this, result -> {
                switch (result) {
                    case Contacts.STATE_SUCCESS: {
                        LoadingFragment.hidden();
                        setResult(RESULT_OK);
                        finish();
                        break;
                    }
                    case Contacts.STATE_POST_TITLE_NULL: {
                        LoadingFragment.hidden();
                        Toast.makeText(this, R.string.post_record_result_title_null, Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            });
        }

        mTvTitle.setText(isEdit ? R.string.title_edit_record : R.string.title_add_record);
    }

    /**
     * 启动日期设置页面
     */
    private void gotoSetDate() {
        if (mDate != null) {
            startActivityForResult(DatePickerActivity.getIntent(this, mDate.getTime()), Contacts.RESULT_TAG_DATEPICKER);
        } else {
            startActivityForResult(DatePickerActivity.getIntent(this), Contacts.RESULT_TAG_DATEPICKER);
        }
        overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
    }

    private void setDate(long date) {
        if (date <= 0) {
            this.mDate = null;
        } else {
            this.mDate = new Date(date);
        }
        if (mDate != null) {
            mTvDate.setText(DateFormat.format("yyyy-MM-dd", mDate));
        } else {
            mTvDate.setText(R.string.record_date_empty);
        }
    }
}
