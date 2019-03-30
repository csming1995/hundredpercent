package com.csming.percent.record

import androidx.lifecycle.ViewModel
import com.csming.percent.common.di.ViewModelKey
import com.csming.percent.record.viewmodel.AddRecordViewModel
import com.csming.percent.record.viewmodel.RecordsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class RecordModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddRecordViewModel::class)
    internal abstract fun bindAddRecordViewModel(viewModelAdd: AddRecordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecordsViewModel::class)
    internal abstract fun bindRecordsViewModel(viewModelAdd: RecordsViewModel): ViewModel
}
