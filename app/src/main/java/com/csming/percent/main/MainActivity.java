package com.csming.percent.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.csming.percent.R;
import com.csming.percent.common.AnalyticsUtil;
import com.csming.percent.common.ApplicationConfig;
import com.csming.percent.common.Contacts;
import com.csming.percent.common.adapter.ViewPagerAdapter;
import com.csming.percent.common.widget.colorfulnavigation.ColorfulNavigation;
import com.csming.percent.main.fragment.PlanFragment;
import com.csming.percent.main.fragment.RecordFragment;
import com.csming.percent.main.viewmodel.MainViewModel;
import com.csming.percent.plan.AddPlanActivity;
import com.csming.percent.plan.vo.ColorEntity;
import com.csming.percent.setting.SettingActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private static final int FRAGMENT_ID_1 = 0;
    private static final int FRAGMENT_ID_2 = 1;
    private static final int[] FRAGMENT_IDS = new int[]{FRAGMENT_ID_1, FRAGMENT_ID_2};

    private CoordinatorLayout mClRoot;

    private ColorfulNavigation mColorfulNavigation;

    private FloatingActionButton mFabAddPlan;

    private Toolbar toolbar;

    private List<Fragment> mFragments;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    @Inject
    ViewModelProvider.Factory factory;

    private MainViewModel mMainViewModel;

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 全屏显示
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initToolBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyticsUtil.onResume(this);
        initData();
    }

//    @Override
//    public void onBackPressed() {
//        if (mAdapterPlans == null || !mAdapterPlans.clearDeleteState()) {
//            super.onBackPressed();
//        }
//    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyticsUtil.onPause(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_info) {
            startActivity(SettingActivity.getIntent(this));
            overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Contacts.RESULT_TAG_ADDED_PLAN) {
            if (resultCode == RESULT_OK) {
                if (ApplicationConfig.getIsFirstAddPlan(this)) {
                    Snackbar.make(mClRoot, R.string.toast_first_add_plan, Snackbar.LENGTH_SHORT).show();
                    ApplicationConfig.setIsFirstAddPlan(this);
                } else {
                    Snackbar.make(mClRoot, R.string.post_plan_result_success, Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }

    }

    private void initView() {
        mClRoot = findViewById(R.id.cl_root);

        mColorfulNavigation = findViewById(R.id.color_navigation);

        mViewPager = findViewById(R.id.viewpager_main);

        mFabAddPlan = findViewById(R.id.fab_add_plan);

        mFabAddPlan.setOnClickListener(v -> {
            startActivityForResult(AddPlanActivity.getIntent(this), Contacts.RESULT_TAG_ADDED_PLAN);
            overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
        });

        // 初始化viewpager
        mFragments = new ArrayList<>(2);
        mFragments.add(PlanFragment.getInstance());
        mFragments.add(RecordFragment.getInstance());
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);

        // 顶部的tab
        mColorfulNavigation.add(new ColorfulNavigation.Item(FRAGMENT_ID_1, R.color.color_select_18, getString(R.string.title_plan)));
        mColorfulNavigation.add(new ColorfulNavigation.Item(FRAGMENT_ID_2, R.color.color_select_18, getString(R.string.title_daily_records)));

        mColorfulNavigation.setSelectedItem(0);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mColorfulNavigation.setSelectedItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mColorfulNavigation.setOnItemSelectedListener(item -> {
            mViewPager.setCurrentItem(item.getId());
        });

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mMainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);

        if (ApplicationConfig.getIsFirstIn(this)) {
            mMainViewModel.initPlan(
                    getResources().getString(R.string.first_plan_title),
                    "",
                    getResources().getColor(ColorEntity.COLOR_VALUES[0])
            );
            ApplicationConfig.setIsFirstIn(this);
        }
    }
}
