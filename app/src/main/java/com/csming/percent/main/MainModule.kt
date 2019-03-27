package com.csming.percent.main

import androidx.lifecycle.ViewModel
import com.csming.percent.common.di.FragmentScoped
import com.csming.percent.common.di.ViewModelKey
import com.csming.percent.main.fragment.PlansFragment
import com.csming.percent.main.fragment.RecordFragment
import com.csming.percent.main.fragment.SettingFragment
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
    internal abstract fun contributePlansFragment(): PlansFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [])
    internal abstract fun contributeRecordFragment(): RecordFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [])
    internal abstract fun contributeSettingFragment(): SettingFragment
}
