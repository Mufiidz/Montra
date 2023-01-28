package com.mufidz.montra.di

import com.mufidz.montra.datamanager.DataManager
import com.mufidz.montra.usecase.*
import com.mufidz.montra.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideAddReport(
        dispatcherProvider: DispatcherProvider,
        dataManager: DataManager
    ): AddReportUseCase = AddReportUseCase(dispatcherProvider, dataManager)

    @Provides
    @Singleton
    fun provideGetListReport(
        dispatcherProvider: DispatcherProvider,
        dataManager: DataManager
    ): ListReportUseCase = ListReportUseCase(dispatcherProvider, dataManager)

    @Provides
    @Singleton
    fun provideDeleteById(
        dispatcherProvider: DispatcherProvider,
        dataManager: DataManager
    ): DeleteByIdUseCase = DeleteByIdUseCase(dispatcherProvider, dataManager)

    @Provides
    @Singleton
    fun provideUpdateReport(
        dispatcherProvider: DispatcherProvider,
        dataManager: DataManager
    ): UpdateUseCase = UpdateUseCase(dispatcherProvider, dataManager)

    @Provides
    @Singleton
    fun provideGetDashboard(
        dispatcherProvider: DispatcherProvider,
        dataManager: DataManager
    ): DashboardUseCase = DashboardUseCase(dispatcherProvider, dataManager)

}