package com.mufidz.montra.di

import com.mufidz.montra.datamanager.DataManager
import com.mufidz.montra.datamanager.DataManagerImpl
import com.mufidz.montra.repository.ReportRepository
import com.mufidz.montra.repository.ReportRepositoryImpl
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