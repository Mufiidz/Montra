package com.mufidz.montra.di

import com.mufidz.montra.repository.ReportRepository
import com.mufidz.montra.repository.ReportRepositoryImpl
import com.mufidz.montra.datamanager.PreferencesDataManagerRepository
import com.mufidz.montra.datamanager.PreferencesDataManagerRepositoryImpl
import com.mufidz.montra.datamanager.DataManager
import com.mufidz.montra.datamanager.DataManagerImpl
import com.mufidz.montra.repository.PreferencesRepository
import com.mufidz.montra.repository.PreferencesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Binds
    abstract fun providePreferencesDataManagerRepository(
        preferencesDataManagerRepositoryImpl: PreferencesDataManagerRepositoryImpl
    ): PreferencesDataManagerRepository

    @Singleton
    @Binds
    abstract fun providePreferencesRepository(
        preferencesRepositoryImpl: PreferencesRepositoryImpl
    ) : PreferencesRepository
}