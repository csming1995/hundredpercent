package com.csming.percent.di

import com.csming.percent.repository.PlanRepository
import com.csming.percent.repository.RecordRepository
import com.csming.percent.repository.impl.PlanRepositoryImpl
import com.csming.percent.repository.impl.RecordRepositoryImpl
import dagger.Binds
import dagger.Module

/**
 * @author Created by csming on 2019/1/19.
 */
@Module
internal abstract class RepositoryModule {

    @Binds
    internal abstract fun bindPlanRepository(repository: PlanRepositoryImpl): PlanRepository

    @Binds
    internal abstract fun bindRecordRepository(repository: RecordRepositoryImpl): RecordRepository
//
//    @Binds
//    internal abstract fun bindLoginRepository(repository: LoginRepositoryImpl): LoginRepository
//
//    @Binds
//    internal abstract fun bindUserRepository(repository: UserRepositoryImpl): UserRepository
}
