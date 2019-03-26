package com.csming.percent.di

import com.csming.percent.PercentApplication
import com.csming.percent.common.di.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        RepositoryModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class]
)
interface AppComponent : AndroidInjector<PercentApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<PercentApplication>()
}
