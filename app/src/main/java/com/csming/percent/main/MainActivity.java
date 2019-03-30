package com.csming.percent.main;

import android.os.Bundle;
import android.view.WindowManager;

import com.csming.percent.R;
import com.csming.percent.common.adapter.ViewPagerAdapter;
import com.csming.percent.common.widget.colornavigation.ColorfulNavigation;
import com.csming.percent.main.fragment.PlansFragment;
import com.csming.percent.main.fragment.RecordFragment;
import com.csming.percent.main.fragment.SettingFragment;
import com.csming.percent.main.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private static final int NAVIGATION_ID_1 = 0;
    private static final int NAVIGATION_ID_2 = 1;
    private static final int NAVIGATION_ID_3 = 2;

    private ColorfulNavigation mNavigation;

    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    private List<Fragment> mFragments;

    @Inject
    ViewModelProvider.Factory factory;

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 全屏显示
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewModel();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initViewModel() {
        mainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
    }

    private void initView() {
        mNavigation = findViewById(R.id.navigation);

        mViewPager = findViewById(R.id.viewpager_main);

        mFragments = new ArrayList<>(2);
        mFragments.add(PlansFragment.getInstance());
        mFragments.add(RecordFragment.getInstance());
        mFragments.add(SettingFragment.getInstance());

        mNavigation.add(new ColorfulNavigation.Item(NAVIGATION_ID_1, R.drawable.ic_book, R.color.color_navigation, null));
        mNavigation.add(new ColorfulNavigation.Item(NAVIGATION_ID_2, R.drawable.ic_home, R.color.color_navigation, null));
        mNavigation.add(new ColorfulNavigation.Item(NAVIGATION_ID_3, R.drawable.ic_setting, R.color.color_navigation, null));

        mNavigation.setSelectedItem(1);

        mNavigation.setOnItemSelectedListener(item -> {
            switch (item.getId()) {
                case NAVIGATION_ID_1: {
                    mViewPager.setCurrentItem(NAVIGATION_ID_1);
                    break;
                }
                case NAVIGATION_ID_2: {
                    mViewPager.setCurrentItem(NAVIGATION_ID_2);
                    break;
                }
                case NAVIGATION_ID_3: {
                    mViewPager.setCurrentItem(NAVIGATION_ID_3);
                    break;
                }
                default:{
                    break;
                }
            }
        });

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(1);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                mNavigation.setSelectedItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }
}
