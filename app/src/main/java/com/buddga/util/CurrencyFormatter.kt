package com.buddga.util

import com.buddga.data.local.SettingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

data class CurrencyConfig(
    val code: String = "USD",
    val symbol: String = "$",
    val locale: Locale = Locale.US
)

@Singleton
class CurrencyFormatter @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) {
    val currencyConfig: Flow<CurrencyConfig> = combine(
        settingsDataStore.currencyCode,
        settingsDataStore.currencySymbol,
        settingsDataStore.currencyLocale
    ) { code, symbol, localeStr ->
        val locale = parseLocale(localeStr)
        CurrencyConfig(code = code, symbol = symbol, locale = locale)
    }

    companion object {
        fun format(amount: BigDecimal, config: CurrencyConfig): String {
            val formatter = NumberFormat.getCurrencyInstance(config.locale)
            try {
                formatter.currency = Currency.getInstance(config.code)
            } catch (_: Exception) {
                // Fallback to locale default
            }
            return formatter.format(amount)
        }

        fun formatCompact(amount: BigDecimal, config: CurrencyConfig): String {
            return "${config.symbol}${String.format(config.locale, "%,.2f", amount)}"
        }

        private fun parseLocale(localeStr: String): Locale {
            val parts = localeStr.split("_")
            return when (parts.size) {
                1 -> Locale(parts[0])
                2 -> Locale(parts[0], parts[1])
                else -> Locale.US
            }
        }
    }
}
