package com.csming.percent.common;

import android.content.Context;

import com.csming.percent.BuildConfig;

/**
 * @author Created by csming on 2018/10/4.
 */
public class ApplicationConfig {

    private static final String PREFERENCE_NAME = BuildConfig.APPLICATION_ID + "_preferecne";

    public static void setIsFirstIn(Context context) {
        context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean("isFirstIn", false).apply();
    }

    public static boolean getIsFirstIn(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean("isFirstIn", true);
    }

}
