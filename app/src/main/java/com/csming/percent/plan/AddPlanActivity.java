package com.csming.percent.plan;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.csming.percent.R;
import com.csming.percent.common.widget.AutofitRecyclerView;
import com.csming.percent.plan.adapter.ColorSelectAdapter;
import com.csming.percent.plan.vo.ColorEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * @author Created by csming on 2019/3/29.
 */
public class AddPlanActivity extends DaggerAppCompatActivity {

    public static Intent getIntent(Context context) {
        return new Intent(context, AddPlanActivity.class);
    }

    private AutofitRecyclerView mRvColorSelectList;
    private ColorSelectAdapter mColorSelectAdapter;
    private List<ColorEntity> colorEntities;

    private int mColorIndex = 0;

    private LinearLayout mLlBackground;
    private LinearLayout mLlRoot;
    private FloatingActionButton mFabAdd;

    private ObjectAnimator mObjectAnimatorCardPanelEnter;
    private ObjectAnimator mObjectAnimatorFabEnter;
    private ObjectAnimator mObjectAnimatorCardPannelExit;
    private ObjectAnimator mObjectAnimatorFabExit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        initView();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        mObjectAnimatorCardPannelExit.start();
        mObjectAnimatorFabExit.start();
    }

    private void initAnimator() {
        int heightCardPanel = mLlRoot.getMeasuredHeight();
        mObjectAnimatorCardPanelEnter = ObjectAnimator.ofFloat(mLlRoot, "translationY", heightCardPanel, -50, 0);
        mObjectAnimatorCardPanelEnter.setDuration(500);

        mObjectAnimatorFabEnter = ObjectAnimator.ofFloat(mFabAdd, "translationY", 500, -50, 0);
        mObjectAnimatorFabEnter.setDuration(600);

        mObjectAnimatorCardPannelExit = ObjectAnimator.ofFloat(mLlRoot, "translationY", 0, heightCardPanel + heightCardPanel + mLlRoot.getTop());
        mObjectAnimatorCardPannelExit.setDuration(200);

        mObjectAnimatorFabExit = ObjectAnimator.ofFloat(mFabAdd, "translationY", 0, heightCardPanel);
        mObjectAnimatorFabExit.setDuration(200);

        mObjectAnimatorCardPannelExit.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
            }
        });
    }

    private void initView() {
        mLlBackground = findViewById(R.id.ll_background);
        mLlRoot = findViewById(R.id.ll_root);
        mFabAdd = findViewById(R.id.fab_add);

        mRvColorSelectList = findViewById(R.id.rv_color_select_list);
        mRvColorSelectList.setColumnWidth(getResources().getDimensionPixelSize(R.dimen.width_per_color_item));

        mLlRoot.post(() -> {
            initAnimator();

            mObjectAnimatorCardPanelEnter.start();
            mObjectAnimatorFabEnter.start();
        });


        colorEntities = new ArrayList<>();
        for (int index = 0; index < ColorEntity.COLOR_VALUES.length; index++) {
            ColorEntity colorEntity = new ColorEntity();
            colorEntity.setColorValue(ColorEntity.COLOR_VALUES[index]);
            colorEntities.add(colorEntity);
        }

        mColorSelectAdapter = new ColorSelectAdapter(colorEntities);
        mRvColorSelectList.setAdapter(mColorSelectAdapter);
        mColorSelectAdapter.setOnItemClickListener((view, position) -> {
            mColorIndex = position;
            mColorSelectAdapter.setSelectIndex(position);
        });

        mLlBackground.setOnClickListener(view -> {
            onBackPressed();
        });

        mFabAdd.setOnClickListener(v -> {
            Toast.makeText(this, "Add Plan", Toast.LENGTH_SHORT).show();
        });
    }
}
