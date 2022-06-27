package com.mufidz.montra.di

import android.content.Context
import com.mufidz.montra.data.local.MontraDb
import com.mufidz.montra.data.local.ReportDao
import com.mufidz.montra.screen.*
import com.mufidz.montra.utils.DispatcherProvider
import com.mufidz.montra.utils.DispatcherProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideReportDao(@ApplicationContext context: Context): ReportDao =
        MontraDb(context).reportDao()

    @Provides
    @Singleton
    fun provideDispatcherProvider()  : DispatcherProvider = DispatcherProviderImpl()
}