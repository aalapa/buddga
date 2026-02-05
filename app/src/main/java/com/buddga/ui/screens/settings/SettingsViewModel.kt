package com.buddga.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buddga.data.local.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CurrencyOption(
    val code: String,
    val symbol: String,
    val name: String,
    val locale: String
)

data class SettingsUiState(
    val selectedCurrencyCode: String = "USD",
    val selectedCurrencySymbol: String = "$",
    val selectedLocale: String = "en_US"
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    val currencyOptions = listOf(
        CurrencyOption("USD", "$", "US Dollar", "en_US"),
        CurrencyOption("EUR", "\u20AC", "Euro", "de_DE"),
        CurrencyOption("GBP", "\u00A3", "British Pound", "en_GB"),
        CurrencyOption("INR", "\u20B9", "Indian Rupee", "en_IN"),
        CurrencyOption("JPY", "\u00A5", "Japanese Yen", "ja_JP"),
        CurrencyOption("CAD", "CA$", "Canadian Dollar", "en_CA"),
        CurrencyOption("AUD", "A$", "Australian Dollar", "en_AU"),
        CurrencyOption("CHF", "CHF", "Swiss Franc", "de_CH"),
        CurrencyOption("CNY", "\u00A5", "Chinese Yuan", "zh_CN"),
        CurrencyOption("BRL", "R$", "Brazilian Real", "pt_BR"),
        CurrencyOption("MXN", "MX$", "Mexican Peso", "es_MX"),
        CurrencyOption("SGD", "S$", "Singapore Dollar", "en_SG"),
        CurrencyOption("KRW", "\u20A9", "South Korean Won", "ko_KR"),
        CurrencyOption("SEK", "kr", "Swedish Krona", "sv_SE"),
        CurrencyOption("NOK", "kr", "Norwegian Krone", "nb_NO"),
        CurrencyOption("ZAR", "R", "South African Rand", "en_ZA")
    )

    val uiState: StateFlow<SettingsUiState> = combine(
        settingsDataStore.currencyCode,
        settingsDataStore.currencySymbol,
        settingsDataStore.currencyLocale
    ) { code, symbol, locale ->
        SettingsUiState(
            selectedCurrencyCode = code,
            selectedCurrencySymbol = symbol,
            selectedLocale = locale
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsUiState()
    )

    fun setCurrency(option: CurrencyOption) {
        viewModelScope.launch {
            settingsDataStore.setCurrency(
                code = option.code,
                symbol = option.symbol,
                locale = option.locale
            )
        }
    }
}
