package com.buddga.ui.screens.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buddga.domain.model.MonthlyCashFlow
import com.buddga.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class TrendsUiState(
    val monthlyTrends: List<MonthlyCashFlow> = emptyList(),
    val avgIncome: BigDecimal = BigDecimal.ZERO,
    val avgExpenses: BigDecimal = BigDecimal.ZERO,
    val savingsRate: Float = 0f,
    val bestMonth: String = "",
    val bestMonthNet: BigDecimal = BigDecimal.ZERO,
    val worstMonth: String = "",
    val worstMonthNet: BigDecimal = BigDecimal.ZERO,
    val periodMonths: Int = 6,
    val isLoading: Boolean = true
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _periodMonths = MutableStateFlow(6)
    private val monthFormatter = DateTimeFormatter.ofPattern("MMM yyyy")

    val uiState: StateFlow<TrendsUiState> = _periodMonths.flatMapLatest { period ->
        val months = (0 until period).map { monthsAgo ->
            YearMonth.now().minusMonths(monthsAgo.toLong())
        }.reversed()

        val monthlyFlows = months.map { month ->
            val start = month.atDay(1)
            val end = month.atEndOfMonth()
            combine(
                transactionRepository.getTotalIncome(start, end),
                transactionRepository.getTotalExpenses(start, end)
            ) { income, expenses ->
                MonthlyCashFlow(month = month, income = income, expenses = expenses)
            }
        }

        if (monthlyFlows.isEmpty()) {
            flowOf(TrendsUiState(isLoading = false))
        } else {
            combine(monthlyFlows) { monthlyDataArray ->
                val monthlyData = monthlyDataArray.toList()
                val count = monthlyData.size.toBigDecimal()

                val totalIncome = monthlyData.sumOf { it.income }
                val totalExpenses = monthlyData.sumOf { it.expenses }
                val avgIncome = if (count > BigDecimal.ZERO) totalIncome.divide(count, 2, RoundingMode.HALF_UP) else BigDecimal.ZERO
                val avgExpenses = if (count > BigDecimal.ZERO) totalExpenses.divide(count, 2, RoundingMode.HALF_UP) else BigDecimal.ZERO

                val savingsRate = if (totalIncome > BigDecimal.ZERO) {
                    ((totalIncome - totalExpenses).toFloat() / totalIncome.toFloat() * 100f)
                } else 0f

                val bestMonthData = monthlyData.maxByOrNull { it.net }
                val worstMonthData = monthlyData.minByOrNull { it.net }

                TrendsUiState(
                    monthlyTrends = monthlyData,
                    avgIncome = avgIncome,
                    avgExpenses = avgExpenses,
                    savingsRate = savingsRate,
                    bestMonth = bestMonthData?.month?.format(monthFormatter) ?: "",
                    bestMonthNet = bestMonthData?.net ?: BigDecimal.ZERO,
                    worstMonth = worstMonthData?.month?.format(monthFormatter) ?: "",
                    worstMonthNet = worstMonthData?.net ?: BigDecimal.ZERO,
                    periodMonths = period,
                    isLoading = false
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TrendsUiState()
    )

    fun setPeriod(months: Int) {
        _periodMonths.value = months
    }
}
