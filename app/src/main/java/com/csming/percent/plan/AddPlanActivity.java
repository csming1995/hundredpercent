package com.csming.percent.plan;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

    private LinearLayout mLlBackground;
    private LinearLayout mLlRoot;
    private FloatingActionButton mFabAdd;

    private int mColorIndex = 0;

    private ObjectAnimator mObjectAnimatorCardPanelEnter;
    private ObjectAnimator mObjectAnimatorFabEnter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

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

    private void initAnimator() {
        int heightCardPanel = mLlRoot.getMeasuredHeight();
        mObjectAnimatorCardPanelEnter = ObjectAnimator.ofFloat(mLlRoot, "translationY", heightCardPanel, -50, 0);
        mObjectAnimatorCardPanelEnter.setDuration(500);

        mObjectAnimatorFabEnter = ObjectAnimator.ofFloat(mFabAdd, "translationY", 500, -50, 0);
        mObjectAnimatorFabEnter.setDuration(600);
    }

    private void initView() {
        mLlBackground = findViewById(R.id.ll_background);
        mLlRoot = findViewById(R.id.ll_root);
        mFabAdd = findViewById(R.id.fab_add);

        mLlRoot.post(() -> {
            initAnimator();

            mObjectAnimatorCardPanelEnter.start();
            mObjectAnimatorFabEnter.start();
        });

        mLlBackground.setOnClickListener(view -> {
            onBackPressed();
        });

        mFabAdd.setOnClickListener(v -> {
            Toast.makeText(this, "Add Plan", Toast.LENGTH_SHORT).show();
        });
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

        mColorEntities = new ArrayList<>();
        for (int index = 0; index < ColorEntity.COLOR_VALUES.length; index++) {
            ColorEntity colorEntity = new ColorEntity();
            colorEntity.setColorValue(ColorEntity.COLOR_VALUES[index]);
            mColorEntities.add(colorEntity);
        }
        colorSelectAdapter = new ColorSelectAdapter(mColorEntities);
        rvColorSelectList.setAdapter(colorSelectAdapter);
        colorSelectAdapter.setOnItemClickListener((view, position) -> {
            mColorIndex = position;
        });
    }
}
