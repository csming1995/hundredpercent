package com.csming.percent.plan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.csming.percent.R;
import com.csming.percent.SlideTouchEventListener;
import com.csming.percent.common.AnalyticsUtil;
import com.csming.percent.common.Contacts;
import com.csming.percent.common.LoadingFragment;
import com.csming.percent.plan.viewmodel.AddPlanViewModel;
import com.csming.percent.plan.vo.ColorEntity;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * @author Created by csming on 2019/3/29.
 */
public class AddPlanActivity extends DaggerAppCompatActivity {

    private static final String EXTRA_TAG_IS_EDIT = "EXTRA_TAG_IS_EDIT";
    private static final String EXTRA_TAG_PLAN_ID = "EXTRA_TAG_PLAN_ID";

    public static Intent getIntent(Context context) {
        return new Intent(context, AddPlanActivity.class);
    }

    public static Intent getIntent(Context context, boolean isEdit, int planId) {
        Intent intent = new Intent(context, AddPlanActivity.class);
        intent.putExtra(EXTRA_TAG_IS_EDIT, isEdit);
        intent.putExtra(EXTRA_TAG_PLAN_ID, planId);
        return intent;
    }

    private LinearLayout mLlRoot;
    //    private FloatingActionButton mFabAdd;
    private EditText mEtTitle;
    private EditText mEtDescription;

    private Toolbar toolbar;

//    private ObjectAnimator mObjectAnimatorCardPanelEnter;
//    private ObjectAnimator mObjectAnimatorFabEnter;

    private SlideTouchEventListener mSlideTouchEventListener;

    @Inject
    ViewModelProvider.Factory factory;

    private AddPlanViewModel mAddPlanViewModel;

    private boolean isEdit;

//    private ColorSelectAdapter mColorSelectAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        initViewModel();
        initView();
//        initColorPanel();
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mSlideTouchEventListener != null) {
            mSlideTouchEventListener.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_post) {
            LoadingFragment.show(getSupportFragmentManager());
            if (isEdit) {
                mAddPlanViewModel.updatePlan(mEtTitle.getText().toString(), mEtDescription.getText().toString());
            } else {
                int ran = (int) (Math.random() * ColorEntity.COLOR_VALUES.length);
                mAddPlanViewModel.postPlan(
                        mEtTitle.getText().toString(),
                        mEtDescription.getText().toString(),
                        getResources().getColor(ColorEntity.COLOR_VALUES[ran]));
            }
        }
        return super.onOptionsItemSelected(item);
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

        toolbar.setTitle(isEdit ? R.string.title_edit_plan : R.string.title_add_plan);

    }


    private void initAnimator() {
//        int heightCardPanel = mLlRoot.getMeasuredHeight();
//        mObjectAnimatorCardPanelEnter = ObjectAnimator.ofFloat(mLlRoot, "translationY", heightCardPanel, -50, 0);
//        mObjectAnimatorCardPanelEnter.setDuration(300);
//
//        mObjectAnimatorFabEnter = ObjectAnimator.ofFloat(mFabAdd, "translationY", 500, -50, 0);
//        mObjectAnimatorFabEnter.setDuration(400);
    }

    private void initView() {
        mLlRoot = findViewById(R.id.ll_root);
//        mFabAdd = findViewById(R.id.fab_add);

        mEtTitle = findViewById(R.id.et_title);
        mEtDescription = findViewById(R.id.et_description);

//        mLlRoot.post(() -> {
//            initAnimator();

//            mObjectAnimatorCardPanelEnter.start();
//            mObjectAnimatorFabEnter.start();
//        });
//
//        mFabAdd.setOnClickListener(v -> {
//            LoadingFragment.show(getSupportFragmentManager());
//            if (isEdit) {
//                mAddPlanViewModel.updatePlan(mEtTitle.getText().toString(), mEtDescription.getText().toString());
//            } else {
//                int ran = (int) (Math.random() * SlogonEntity.COLOR_VALUES.length);
//                mAddPlanViewModel.postPlan(
//                        mEtTitle.getText().toString(),
//                        mEtDescription.getText().toString(),
//                        getResources().getColor(SlogonEntity.COLOR_VALUES[ran]));
//            }
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
    }

//    /**
//     * 初始化颜料盘
//     */
//    private void initColorPanel() {
//        AutofitRecyclerView rvColorSelectList;
//        List<SlogonEntity> mColorEntities;
//
//        rvColorSelectList = findViewById(R.id.rv_color_select_list);
//        rvColorSelectList.setColumnWidth(getResources().getDimensionPixelSize(R.dimen.width_per_color_item));
//
//        // 颜色数据
//        mColorEntities = new ArrayList<>();
//        for (int index = 0; index < SlogonEntity.COLOR_VALUES.length; index++) {
//            SlogonEntity colorEntity = new SlogonEntity();
//            colorEntity.setColorValue(SlogonEntity.COLOR_VALUES[index]);
//            mColorEntities.add(colorEntity);
//        }
//        mColorSelectAdapter = new ColorSelectAdapter(mColorEntities);
//        rvColorSelectList.setAdapter(mColorSelectAdapter);
//        mColorSelectAdapter.setOnItemClickListener((view, position, colorEntity) -> {
//            int color = getResources().getColor(colorEntity.getColorValue());
//            mAddPlanViewModel.setColor(color);
//        });
//    }

    private void initViewModel() {
        mAddPlanViewModel = ViewModelProviders.of(this, factory).get(AddPlanViewModel.class);
        mAddPlanViewModel.setColor(getResources().getColor(ColorEntity.COLOR_VALUES[0]));

        isEdit = getIntent().getBooleanExtra(EXTRA_TAG_IS_EDIT, false);
        if (isEdit) {
            mAddPlanViewModel.setPlanId(getIntent().getIntExtra(EXTRA_TAG_PLAN_ID, 0));
            mAddPlanViewModel.getPlan().observe(this, plan -> {
                mEtTitle.setText(plan.getTitle());
                mEtDescription.setText(plan.getDescription());
                mAddPlanViewModel.setColor(plan.getColor());
//                for (int index = 0; index < SlogonEntity.COLOR_VALUES.length; index++) {
//                    if (getResources().getColor(SlogonEntity.COLOR_VALUES[index]) == plan.getColor()) {
//                        mColorSelectAdapter.setSelectIndex(index);
//                        break;
//                    }
//                }
            });

            mAddPlanViewModel.getStateLiveData().observe(this, state -> {
                switch (state) {
                    case Contacts.STATE_SUCCESS: {
                        LoadingFragment.hidden();
                        Toast.makeText(this, R.string.update_plan_result_success, Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        break;
                    }
                    case Contacts.STATE_UPDATE_TITLE_NULL: {
                        LoadingFragment.hidden();
                        Toast.makeText(this, R.string.post_plan_result_title_null, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case Contacts.STATE_POST_PLAN_EXIST: {
                        LoadingFragment.hidden();
                        Toast.makeText(this, R.string.post_plan_result_plan_exist, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            mAddPlanViewModel.getStateLiveData().observe(this, state -> {
                switch (state) {
                    case Contacts.STATE_SUCCESS: {
                        LoadingFragment.hidden();
                        Toast.makeText(this, R.string.post_plan_result_success, Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        break;
                    }
                    case Contacts.STATE_POST_TITLE_NULL: {
                        LoadingFragment.hidden();
                        Toast.makeText(this, R.string.post_plan_result_title_null, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case Contacts.STATE_POST_PLAN_EXIST: {
                        LoadingFragment.hidden();
                        Toast.makeText(this, R.string.post_plan_result_plan_exist, Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
//        ((TextView)findViewById(R.id.tv_title)).setText(isEdit ? R.string.title_edit_plan: R.string.title_add_plan);

    }
}
