package com.csming.percent.di

import android.content.ClipboardManager
import android.content.Context
import android.net.wifi.WifiManager
import androidx.room.Room
import com.csming.percent.PercentApplication
import com.csming.percent.common.AppExecutors
import com.csming.percent.data.PercentDatabase
import com.csming.percent.data.dao.PlanDao
import com.csming.percent.data.dao.RecordDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideContext(application: PercentApplication): Context {
        return application.applicationContext
    }

    @Provides
    fun providesWifiManager(context: Context): WifiManager =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    @Provides
    fun providesClipboardManager(context: Context): ClipboardManager =
        context.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE)
            as ClipboardManager

    @Provides
    fun providesAppExecutors(): AppExecutors {
        return AppExecutors()
    }

    @Singleton
    @Provides
    fun providerAppDatabase(context: Context): PercentDatabase {
        return Room.databaseBuilder(context, PercentDatabase::class.java, "hundredpercent.db")
                .allowMainThreadQueries()
                .build()
//        return AppDatabase.getDefault(context.applicationContext)
    }

    @Singleton
    @Provides
    fun providerPlanDao(appDatabase: PercentDatabase): PlanDao {
        return appDatabase.planDao
    }

    @Singleton
    @Provides
    fun providerRecordDao(appDatabase: PercentDatabase): RecordDao {
        return appDatabase.recordDao
    }
}
