package com.csming.percent.setting;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.csming.percent.R;
import com.csming.percent.SlideTouchEventListener;
import com.csming.percent.common.AnalyticsUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * @author Created by csming on 2019/3/29.
 */
public class SettingActivity extends AppCompatActivity {

    public static Intent getIntent(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    private LinearLayout mLlRoot;
//    private FloatingActionButton mFabClose;

    //    private LinearLayout mLlGotoWeibo;
    private LinearLayout mLlGotoEmail;
    private LinearLayout mLlUpdate;

    private Toolbar toolbar;

    private ObjectAnimator mObjectAnimatorCardPanelEnter;
//    private ObjectAnimator mObjectAnimatorFabEnter;

    private SlideTouchEventListener mSlideTouchEventListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        initToolBar();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
//        toolbar.setBackgroundColor(getResources().getColor(R.color.color_ffffff));

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setTitle(R.string.title_setting);
        }

    }


    private void initAnimator() {
        int heightCardPanel = mLlRoot.getMeasuredHeight();
        mObjectAnimatorCardPanelEnter = ObjectAnimator.ofFloat(mLlRoot, "translationY", heightCardPanel, -50, 0);
        mObjectAnimatorCardPanelEnter.setDuration(300);

//        mObjectAnimatorFabEnter = ObjectAnimator.ofFloat(mFabClose, "translationY", 500, -50, 0);
//        mObjectAnimatorFabEnter.setDuration(400);
    }

    private void initView() {
        mLlRoot = findViewById(R.id.ll_root);
//        mFabClose = findViewById(R.id.fab_close);

//        mLlGotoWeibo = findViewById(R.id.ll_goto_weibo);
        mLlGotoEmail = findViewById(R.id.ll_goto_email);
        mLlUpdate = findViewById(R.id.ll_update);

        mLlRoot.post(() -> {
            initAnimator();

            mObjectAnimatorCardPanelEnter.start();
//            mObjectAnimatorFabEnter.start();
        });

//        mFabClose.setOnClickListener(v -> {
//            onBackPressed();
//        });

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

//        mLlGotoWeibo.setOnClickListener(view -> {
//            Uri uri = Uri.parse("https://m.weibo.cn/u/1956533563?uid=1956533563");
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            startActivity(intent);
//        });
        mLlGotoEmail.setOnClickListener(view -> {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setAction(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                    new String[]{getString(R.string.setting_about_email)});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    getString(R.string.setting_email_subject));
            startActivity(Intent.createChooser(emailIntent, "Send Email Using: "));
        });

        mLlUpdate.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://www.coolapk.com/apk/com.csming.percent");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

    }
}
