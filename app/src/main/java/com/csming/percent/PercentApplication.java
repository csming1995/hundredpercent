package com.csming.percent;

import com.csming.percent.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
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
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
