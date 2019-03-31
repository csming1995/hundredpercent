package com.csming.percent.di

import com.csming.percent.common.di.ActivityScoped
import com.csming.percent.main.MainActivity
import com.csming.percent.main.MainModule
import com.csming.percent.plan.AddPlanActivity
import com.csming.percent.plan.PlanModule
import com.csming.percent.record.AddRecordActivity
import com.csming.percent.record.RecordModule
import com.csming.percent.record.RecordsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [PlanModule::class])
    internal abstract fun addPlanActivity(): AddPlanActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [RecordModule::class])
    internal abstract fun addRecordActivity(): AddRecordActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [RecordModule::class])
    internal abstract fun addRecordsActivity(): RecordsActivity
}
