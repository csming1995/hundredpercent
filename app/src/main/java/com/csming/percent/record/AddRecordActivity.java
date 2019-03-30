package com.csming.percent.record;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.csming.percent.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * @author Created by csming on 2019/3/29.
 */
public class AddRecordActivity extends DaggerAppCompatActivity {

    public static Intent getIntent(Context context) {
        return new Intent(context, AddRecordActivity.class);
    }

    private LinearLayout mLlBackground;
    private LinearLayout mLlRoot;
    private FloatingActionButton mFabAdd;

    private ObjectAnimator mObjectAnimatorCardPanelEnter;
    private ObjectAnimator mObjectAnimatorFabEnter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        initView();
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

        // 获取 主面板高度
        int heightCardPanel = mLlRoot.getMeasuredHeight();
        // 获取屏幕信息
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
            Toast.makeText(this, "Add Record", Toast.LENGTH_SHORT).show();
        });
    }
}
