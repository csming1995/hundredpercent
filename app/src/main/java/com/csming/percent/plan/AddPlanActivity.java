package com.csming.percent.plan;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.csming.percent.R;
import com.csming.percent.SlideTouchEventListener;
import com.csming.percent.common.widget.AutofitRecyclerView;
import com.csming.percent.main.viewmodel.MainViewModel;
import com.csming.percent.plan.adapter.ColorSelectAdapter;
import com.csming.percent.plan.viewmodel.PlanViewModel;
import com.csming.percent.plan.vo.ColorEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * @author Created by csming on 2019/3/29.
 */
public class AddPlanActivity extends DaggerAppCompatActivity {

    public static Intent getIntent(Context context) {
        return new Intent(context, AddPlanActivity.class);
    }

    private LinearLayout mLlRoot;
    private FloatingActionButton mFabAdd;
    private EditText mEtTitle;
    private EditText mEtDescription;

    private ObjectAnimator mObjectAnimatorCardPanelEnter;
    private ObjectAnimator mObjectAnimatorFabEnter;

    private SlideTouchEventListener mSlideTouchEventListener;

    @Inject
    ViewModelProvider.Factory factory;

    private PlanViewModel mPlanViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        initViewModel();
        initView();
        initColorPanel();
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
        int heightCardPanel = mLlRoot.getMeasuredHeight();
        mObjectAnimatorCardPanelEnter = ObjectAnimator.ofFloat(mLlRoot, "translationY", heightCardPanel, -50, 0);
        mObjectAnimatorCardPanelEnter.setDuration(300);

        mObjectAnimatorFabEnter = ObjectAnimator.ofFloat(mFabAdd, "translationY", 500, -50, 0);
        mObjectAnimatorFabEnter.setDuration(400);
    }

    private void initView() {
        mLlRoot = findViewById(R.id.ll_root);
        mFabAdd = findViewById(R.id.fab_add);

        mEtTitle = findViewById(R.id.et_title);
        mEtDescription = findViewById(R.id.et_description);

        mLlRoot.post(() -> {
            initAnimator();

            mObjectAnimatorCardPanelEnter.start();
            mObjectAnimatorFabEnter.start();
        });

        mFabAdd.setOnClickListener(v -> {
            int result = mPlanViewModel.postPlan(mEtTitle.getText().toString(), mEtDescription.getText().toString());
            switch (result) {
                case PlanViewModel.STATE_POST_SUCCESS: {
                    Toast.makeText(this, R.string.post_result_success, Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    break;
                }
                case PlanViewModel.STATE_POST_TITLE_NULL: {
                    Toast.makeText(this, R.string.post_result_title_null, Toast.LENGTH_SHORT).show();
                    break;
                }
                case PlanViewModel.STATE_POST_PLAN_EXIST: {
                    Toast.makeText(this, R.string.post_result_plan_exist, Toast.LENGTH_SHORT).show();
                }
            }
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
    }

    /**
     * 初始化颜料盘
     */
    private void initColorPanel() {
        AutofitRecyclerView rvColorSelectList;
        ColorSelectAdapter colorSelectAdapter;
        List<ColorEntity> mColorEntities;

        rvColorSelectList = findViewById(R.id.rv_color_select_list);
        rvColorSelectList.setColumnWidth(getResources().getDimensionPixelSize(R.dimen.width_per_color_item));

        // 颜色数据
        mColorEntities = new ArrayList<>();
        for (int index = 0; index < ColorEntity.COLOR_VALUES.length; index++) {
            ColorEntity colorEntity = new ColorEntity();
            colorEntity.setColorValue(ColorEntity.COLOR_VALUES[index]);
            mColorEntities.add(colorEntity);
        }
        colorSelectAdapter = new ColorSelectAdapter(mColorEntities);
        rvColorSelectList.setAdapter(colorSelectAdapter);
        colorSelectAdapter.setOnItemClickListener((view, position, colorEntity) -> {
            int color = getResources().getColor(colorEntity.getColorValue());
            mPlanViewModel.setColor(color);
        });
    }

    private void initViewModel() {
        mPlanViewModel = ViewModelProviders.of(this, factory).get(PlanViewModel.class);
        mPlanViewModel.setColor(getResources().getColor(ColorEntity.COLOR_VALUES[0]));
    }
}
