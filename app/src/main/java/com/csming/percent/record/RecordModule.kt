package com.csming.percent.record

import androidx.lifecycle.ViewModel
import com.csming.percent.common.di.ViewModelKey
import com.csming.percent.record.viewmodel.RecordViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class RecordModule {

    @Binds
    @IntoMap
    @ViewModelKey(RecordViewModel::class)
    internal abstract fun bindPlanViewModel(viewModel: RecordViewModel): ViewModel
}
