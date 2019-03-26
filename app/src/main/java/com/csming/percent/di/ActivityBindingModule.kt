package com.csming.percent.di

import com.csming.percent.common.di.ActivityScoped
import com.csming.percent.main.MainActivity
import com.csming.percent.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun mainActivity(): MainActivity
}
