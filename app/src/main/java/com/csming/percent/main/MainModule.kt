package com.csming.percent.main

import androidx.lifecycle.ViewModel
import com.csming.percent.common.di.FragmentScoped
import com.csming.percent.common.di.ViewModelKey
import com.csming.percent.main.fragment.PlanFragment
import com.csming.percent.main.fragment.RecordFragment
import com.csming.percent.main.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @FragmentScoped
    @ContributesAndroidInjector(modules = [])
    internal abstract fun contributePlanFragment(): PlanFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [])
    internal abstract fun contributeRecordFragment(): RecordFragment
}
