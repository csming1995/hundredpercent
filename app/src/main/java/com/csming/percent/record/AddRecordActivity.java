package com.csming.percent.record;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.csming.percent.R;
import com.csming.percent.SlideTouchEventListener;
import com.csming.percent.record.viewmodel.AddRecordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import androidx.annotation.Nullable;
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

    public static Intent getIntent(Context context, int planId) {
        Intent intent = new Intent(context, AddRecordActivity.class);
        intent.putExtra(EXTRA_TAG_PLAN_ID, planId);
        return intent;
    }

    public static Intent getIntent(Context context, int planId, boolean isEdit, int recordId, String title, String description) {
        Intent intent = new Intent(context, AddRecordActivity.class);
        intent.putExtra(EXTRA_TAG_PLAN_ID, planId);
        intent.putExtra(EXTRA_TAG_IS_EDIT, isEdit);
        intent.putExtra(EXTRA_TAG_RECORD_ID, recordId);
        intent.putExtra(EXTRA_TAG_TITLE, title);
        intent.putExtra(EXTRA_TAG_DESCRIPTION, description);
        return intent;
    }

    private LinearLayout mLlRoot;
    private TextView mTvTitle;
    private FloatingActionButton mFabAdd;

    private EditText mEtTitle;
    private EditText mEtDescription;

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
        mLlRoot = findViewById(R.id.ll_root);
        mTvTitle = findViewById(R.id.tv_title);
        mFabAdd = findViewById(R.id.fab_add);

        mEtTitle = findViewById(R.id.et_title);
        mEtDescription = findViewById(R.id.et_description);

        mLlRoot.post(() -> {
            initAnimator();

            mObjectAnimatorCardPanelEnter.start();
            mObjectAnimatorFabEnter.start();
        });

        mFabAdd.setOnClickListener(v -> {
            if (!isEdit) {
                int result = mAddRecordViewModel.postRecord(mEtTitle.getText().toString(), mEtDescription.getText().toString());
                switch (result) {
                    case AddRecordViewModel.STATE_POST_SUCCESS: {
                        Toast.makeText(this, R.string.post_record_result_success, Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        break;
                    }
                    case AddRecordViewModel.STATE_POST_TITLE_NULL: {
                        Toast.makeText(this, R.string.post_record_result_title_null, Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            } else {
                int result = mAddRecordViewModel.updateRecord(mEtTitle.getText().toString(), mEtDescription.getText().toString());
                switch (result) {
                    case AddRecordViewModel.STATE_UPDATE_SUCCESS: {
                        Toast.makeText(this, R.string.update_record_result_success, Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        break;
                    }
                    case AddRecordViewModel.STATE_UPDATE_TITLE_NULL: {
                        Toast.makeText(this, R.string.post_record_result_title_null, Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
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
            mEtTitle.setText(title);
            mEtDescription.setText(description);
        }

        mTvTitle.setText(isEdit ? R.string.title_edit_record: R.string.title_add_record);
    }
}
