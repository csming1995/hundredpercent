package com.csming.percent.record;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.csming.percent.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * @author Created by csming on 2019/3/29.
 */
public class AddRecordActivity extends DaggerAppCompatActivity {

    public static Intent getIntent(Context context) {
        return new Intent(context, AddRecordActivity.class);
    }

    private CardView mCvPanel;
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
        int heightCardPanel = mCvPanel.getMeasuredHeight();
        mObjectAnimatorCardPanelEnter = ObjectAnimator.ofFloat(mCvPanel, "translationY", heightCardPanel, -50, 0);
        mObjectAnimatorCardPanelEnter.setDuration(500);

        mObjectAnimatorFabEnter = ObjectAnimator.ofFloat(mFabAdd, "translationY", 500, -50, 0);
        mObjectAnimatorFabEnter.setDuration(600);

        mObjectAnimatorCardPannelExit = ObjectAnimator.ofFloat(mCvPanel, "translationY", 0, heightCardPanel + heightCardPanel + mCvPanel.getTop());
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
        mCvPanel = findViewById(R.id.cv_panel);
        mFabAdd = findViewById(R.id.fab_add);

        mCvPanel.post(() -> {
            initAnimator();

            mObjectAnimatorCardPanelEnter.start();
            mObjectAnimatorFabEnter.start();
        });
    }
}
