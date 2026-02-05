package com.buddga.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsDataStore @Inject constructor(
    private val context: Context
) {
    companion object {
        private val CURRENCY_CODE = stringPreferencesKey("currency_code")
        private val CURRENCY_SYMBOL = stringPreferencesKey("currency_symbol")
        private val CURRENCY_LOCALE = stringPreferencesKey("currency_locale")
    }

    val currencyCode: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[CURRENCY_CODE] ?: "USD"
    }

    val currencySymbol: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[CURRENCY_SYMBOL] ?: "$"
    }

    val currencyLocale: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[CURRENCY_LOCALE] ?: "en_US"
    }

    suspend fun setCurrency(code: String, symbol: String, locale: String) {
        context.dataStore.edit { prefs ->
            prefs[CURRENCY_CODE] = code
            prefs[CURRENCY_SYMBOL] = symbol
            prefs[CURRENCY_LOCALE] = locale
        }
    }
}
