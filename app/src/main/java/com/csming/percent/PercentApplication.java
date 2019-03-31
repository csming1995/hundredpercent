package com.csming.percent;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.csming.percent.di.DaggerAppComponent;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * @author Created by csming on 2019/3/26.
 */
public class PercentApplication extends DaggerApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        UMConfigure.init(
                this,
                BuildConfig.DEBUG ? getString(R.string.umeng_appkey_debug) : getString(R.string.umeng_appkey_release),
                "Normal", UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();
        Fabric.with(this, crashlyticsKit);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
