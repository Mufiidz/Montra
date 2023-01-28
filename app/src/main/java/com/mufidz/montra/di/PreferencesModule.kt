package com.mufidz.montra.di

import android.content.SharedPreferences
import com.mufidz.montra.data.prefs.MontraPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {

    @Provides
    @Singleton
    fun provideMontraPreferences(sharedPreferences: SharedPreferences): MontraPreferences =
        MontraPreferences(sharedPreferences)
}