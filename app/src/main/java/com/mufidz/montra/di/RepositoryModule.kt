package com.mufidz.montra.di

import com.mufidz.montra.screen.DataManager
import com.mufidz.montra.screen.DataManagerImpl
import com.mufidz.montra.screen.ReportRepository
import com.mufidz.montra.screen.ReportRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideDataManager(dataManagerImpl: DataManagerImpl): DataManager

    @Singleton
    @Binds
    abstract fun provideReportRepository(reportRepositoryImpl: ReportRepositoryImpl): ReportRepository
}