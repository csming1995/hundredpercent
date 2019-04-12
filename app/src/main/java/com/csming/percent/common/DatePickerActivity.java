package com.csming.percent.common;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.DatePicker;

import com.csming.percent.R;
import com.csming.percent.SlideTouchEventListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import timber.log.Timber;

/**
 * 点击确认，返回一个时间Long型
 * @author Created by csming on 2019/4/12.
 */
public class DatePickerActivity extends AppCompatActivity {

    public static final String TAG_RESULT_DATE = "TAG_RESULT_DATE";
    // 提示时间
    private static final int HOUR_OF_DAY = 9;
    private static final int MINUTE = 0;
    private static final int SECOND = 0;
    private CoordinatorLayout mClRoot;
    private CardView mCvRoot;
    private FloatingActionButton mFabAdd;
    private ObjectAnimator mObjectAnimatorCardPanelEnter;
    private ObjectAnimator mObjectAnimatorFabEnter;
    private SlideTouchEventListener mSlideTouchEventListener;
    private DatePicker mDatePicker;

    public static Intent getIntent(Context context) {
        return new Intent(context, DatePickerActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datepicker);

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mSlideTouchEventListener != null) {
            mSlideTouchEventListener.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    private void initAnimator() {

        // 获取 主面板高度
        int heightCardPanel = mCvRoot.getMeasuredHeight();
//         获取屏幕信息
        mObjectAnimatorCardPanelEnter = ObjectAnimator.ofFloat(mCvRoot, "translationY", heightCardPanel, -50, 0);
        mObjectAnimatorCardPanelEnter.setDuration(500);

        mObjectAnimatorFabEnter = ObjectAnimator.ofFloat(mFabAdd, "translationY", 500, -50, 0);
        mObjectAnimatorFabEnter.setDuration(600);
    }

    private void initView() {
        mClRoot = findViewById(R.id.cl_root_panel);
        mCvRoot = findViewById(R.id.cv_root);
        mFabAdd = findViewById(R.id.fab_add);
        mDatePicker = findViewById(R.id.date_picker);

        mCvRoot.post(() -> {
            initAnimator();

            mObjectAnimatorCardPanelEnter.start();
            mObjectAnimatorFabEnter.start();
        });

        mFabAdd.setOnClickListener(view -> {
            Date date = getDateFromDatePicker(mDatePicker);
            Date date1 = getNowDate();
            if (compareDate(date, date1) < 0) {
                Snackbar.make(mClRoot, R.string.title_error_toast, Snackbar.LENGTH_SHORT).show();
                return;
            }
            Timber.d(date.toString());
            Intent intent = new Intent();
            intent.putExtra(TAG_RESULT_DATE, date.getTime());
            setResult(RESULT_OK, intent);
            finish();
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
    }

    private Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, HOUR_OF_DAY, MINUTE, SECOND);

        return calendar.getTime();
    }

    private Date getNowDate() {
        return new Date();
    }

    private long compareDate(Date date, Date date1) {
        return date.getTime() - date1.getTime();
    }
}
