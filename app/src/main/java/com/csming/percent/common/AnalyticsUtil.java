package com.csming.percent.common;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

/**
 * @author Created by csming on 2019/3/31.
 */
public class AnalyticsUtil {

    public static void onResume(Context context) {
        MobclickAgent.onResume(context);
    }

    public static void onPause(Context context) {
        MobclickAgent.onPause(context);
    }
}
