package com.csming.percent.plan

import androidx.lifecycle.ViewModel
import com.csming.percent.common.di.ViewModelKey
import com.csming.percent.plan.viewmodel.AddPlanViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class PlanModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddPlanViewModel::class)
    internal abstract fun bindPlanViewModel(viewModelAdd: AddPlanViewModel): ViewModel
}
