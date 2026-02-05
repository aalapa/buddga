package com.buddga.di

import android.content.Context
import com.buddga.data.local.SettingsDataStore
import com.buddga.util.CurrencyFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsDataStore(@ApplicationContext context: Context): SettingsDataStore {
        return SettingsDataStore(context)
    }

    @Provides
    @Singleton
    fun provideCurrencyFormatter(settingsDataStore: SettingsDataStore): CurrencyFormatter {
        return CurrencyFormatter(settingsDataStore)
    }
}
