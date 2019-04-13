package com.csming.percent.common.widget.colorfulnavigation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.csming.percent.R;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

/**
 * @author Created by csming on 2019/3/16.
 */
public class ColorfulNavigation extends CardView {

    private LinearLayout mLlRoot;
    private List<ColorfulNavigationItem> mColorfulNavigationItems;
    private List<Item> mItems;

    private int mIndex;
    private int mDrawIndex = 0;

    private int mBackgroundColor;
    private float mBorderWidth;
    private float mBorderRadius;
    private int mTextColor;
    private float mElevation;

    private int mWidth;
    private int mHeight;
    private int mItemWidth;
    private RectF mRectF;
    private RectF mRightRectF;
    private Paint mPaint;
    private Paint mRightPaint;

    private RectF mRectFBorder;
    private Paint mPaintBorder;

    private OnItemSelectedListener mOnItemSelectedListener;
    private OnItemSelectedListener mRealOnItemSelectedListener;

    private ValueAnimator mValueAnimator;
    private ValueAnimatorListener mValueAnimatorListener;

    public ColorfulNavigation(Context context) {
        this(context, null);
    }

    public ColorfulNavigation(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorfulNavigation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttr(context, attrs);

        mLlRoot = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mLlRoot.setLayoutParams(layoutParams);
        mLlRoot.setOrientation(LinearLayout.HORIZONTAL);
        addView(mLlRoot);

        setClickable(false);
        mColorfulNavigationItems = new LinkedList<>();
        mItems = new LinkedList<>();

        setWillNotDraw(false);
        init();

        mOnItemSelectedListener = item -> {
            startAnimator(item.index);

            if (mRealOnItemSelectedListener != null) {
                mRealOnItemSelectedListener.onItemSelected(item);
            }
        };
    }

    private void initAttr(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.ColorNavigation, 0, 0);
        mBackgroundColor = typedArray.getColor(R.styleable.ColorNavigation_color_navigation_background, getResources().getColor(R.color.color_212121));
        mBorderWidth = typedArray.getDimension(R.styleable.ColorNavigation_color_navigation_border_width, getResources().getDimension(R.dimen.color_navigation_default_border_width));
        mBorderRadius = typedArray.getDimension(R.styleable.ColorNavigation_color_navigation_radius, getResources().getDimension(R.dimen.color_navigation_default_radius));
        mTextColor = typedArray.getColor(R.styleable.ColorNavigation_color_navigation_text_color, getResources().getColor(R.color.color_ffffff));
        mElevation = typedArray.getDimension(R.styleable.ColorNavigation_color_navigation_elevation, getResources().getDimension(R.dimen.color_navigation_default_elevation));
        setCardBackgroundColor(mBackgroundColor);
        setRadius(mBorderRadius);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(mElevation);
        }
        typedArray.recycle();
    }

    private void init() {
        mPaint = new Paint();
        mRightPaint = new Paint();
        mPaintBorder = new Paint();

        mPaint.setAntiAlias(true);
        mRightPaint.setAntiAlias(true);
        mPaintBorder.setAntiAlias(true);
        mPaintBorder.setStyle(Paint.Style.STROKE);
        mPaintBorder.setStrokeWidth(mBorderWidth);

        mPaintBorder.setColor(mBackgroundColor);

        mRectF = new RectF();
        mRightRectF = new RectF();
        mRectFBorder = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(mWidth, mHeight);

        mItemWidth = mWidth / mItems.size();
        mRectF.left = mItemWidth * mIndex;
        mRectF.top = 0;
        mRectF.right = mRectF.left + mItemWidth;
        mRectF.bottom = mHeight;

        mRightRectF.left = mRectF.left;
        mRightRectF.top = 0;
        mRightRectF.right = mRectF.right;
        mRightRectF.bottom = mHeight;

        mRectFBorder.left = mBorderWidth / 3;
        mRectFBorder.top = mBorderWidth / 3;
        mRectFBorder.right = mWidth - mBorderWidth / 3;
        mRectFBorder.bottom = mHeight - mBorderWidth / 3;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(mRectF, mPaint);
        canvas.drawRect(mRightRectF, mRightPaint);

        canvas.drawRoundRect(mRectFBorder, getRadius(), getRadius(), mPaintBorder);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mValueAnimator != null) {
            mValueAnimator.removeAllUpdateListeners();
        }
    }

    public void add(@NonNull Item item) {
        ColorfulNavigationItem colorfulNavigationItem = new ColorfulNavigationItem(getContext());
        colorfulNavigationItem.setItem(item);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);
        colorfulNavigationItem.setLayoutParams(layoutParams);
        mLlRoot.addView(colorfulNavigationItem);
        colorfulNavigationItem.setOnSelectedListener(mOnItemSelectedListener);

        mIndex = mItems.size();
        item.index = mIndex;
        mItems.add(item);
        mColorfulNavigationItems.add(colorfulNavigationItem);

        mPaint.setColor(getResources().getColor(item.color));
        mRightPaint.setColor(getResources().getColor(item.color));

        mLlRoot.requestLayout();
    }

    public void setOnItemSelectedListener(final OnItemSelectedListener onItemSelectedListener) {
        this.mRealOnItemSelectedListener = onItemSelectedListener;
    }

    public void setSelectedItem(int index) {
        if (index >= mItems.size()) return;
        mPaint.setColor(getResources().getColor(mItems.get(index).color));
        mRightPaint.setColor(getResources().getColor(mItems.get(index).color));
        invalidate();
        startAnimator(index);
    }

    private void startAnimator(int targetIndex) {
        boolean isRight = mIndex < targetIndex;
        mIndex = targetIndex;
        int targetLeft = mItemWidth * mIndex;
        if (targetLeft != mRectF.left) {
            if (mValueAnimator == null) {
                mValueAnimator = new ValueAnimator();
                mValueAnimatorListener = new ValueAnimatorListener();
                mValueAnimator.addUpdateListener(mValueAnimatorListener);
            }
            mValueAnimator.setFloatValues(mRectF.left, targetLeft/*, targetLeft + (isRight? 20: - 20), targetLeft*/);
            mValueAnimator.setDuration(300);
            mValueAnimator.cancel();
            mValueAnimator.start();
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(Item item);
    }

    public static class Item {
        private int id;
        @ColorRes
        private int color;
        private String title;
        @ColorRes
        private int titleColor;
        private int index = 0;

        public Item(int id, @ColorRes int color, String title) {
            this(id, color, title, R.color.color_ffffff);
        }

        public Item(int id, @ColorRes int color, String title, @ColorRes int titleColor) {
            super();
            this.id = id;
            this.color = color;
            this.title = title;
            this.titleColor = titleColor;
        }

        public int getId() {
            return this.id;
        }

        String getTitle() {
            return this.title;
        }

        int getColor() {
            return this.color;
        }

        int getTitleColor() {
            return this.titleColor;
        }
    }

    private class ValueAnimatorListener implements ValueAnimator.AnimatorUpdateListener {

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            mRectF.left = (float) valueAnimator.getAnimatedValue();
            mRectF.right = mRectF.left + mItemWidth;
            mDrawIndex = (int) mRectF.left / mItemWidth;
            mRightRectF.left = (mDrawIndex + 1) * mItemWidth;
            mRightRectF.right = mRectF.right;
            mPaint.setColor(getResources().getColor(mItems.get(mDrawIndex).getColor()));
            if (mDrawIndex + 1 < mItems.size()) {
                mRightPaint.setColor(getResources().getColor(mItems.get(mDrawIndex + 1).getColor()));
            }
            invalidate();
        }
    }
}
