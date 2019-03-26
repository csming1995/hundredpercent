package com.csming.percent.main;

import android.os.Bundle;

import com.csming.percent.R;
import com.csming.percent.common.widget.colornavigation.ColorfulNavigation;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int NAVIGATION_ID_1 = 1;
    private static final int NAVIGATION_ID_2 = 2;
    private static final int NAVIGATION_ID_3 = 3;

    private ColorfulNavigation mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mNavigation = findViewById(R.id.navigation);

        mNavigation.add(new ColorfulNavigation.Item(NAVIGATION_ID_1, R.drawable.ic_book, R.color.color_navigation, null));
        mNavigation.add(new ColorfulNavigation.Item(NAVIGATION_ID_2, R.drawable.ic_home, R.color.color_navigation, null));
        mNavigation.add(new ColorfulNavigation.Item(NAVIGATION_ID_3, R.drawable.ic_setting, R.color.color_navigation, null));

        mNavigation.setSelectedItem(1);

        mNavigation.setOnItemSelectedListener(item -> {
            switch (item.getId()) {
                case NAVIGATION_ID_1: {

                    break;
                }
                case NAVIGATION_ID_2: {

                    break;
                }
                case NAVIGATION_ID_3: {

                    break;
                }
                default:{
                    break;
                }
            }
        });
    }
}
