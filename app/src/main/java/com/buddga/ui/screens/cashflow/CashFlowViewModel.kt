package com.buddga.ui.screens.cashflow

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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.math.BigDecimal
import java.time.YearMonth
import javax.inject.Inject

data class CashFlowUiState(
    val totalIncome: BigDecimal = BigDecimal.ZERO,
    val totalExpenses: BigDecimal = BigDecimal.ZERO,
    val netCashFlow: BigDecimal = BigDecimal.ZERO,
    val monthlyData: List<MonthlyCashFlow> = emptyList(),
    val periodLabel: String = "Last 6 Months",
    val isLoading: Boolean = true,
    val error: String? = null
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CashFlowViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _period = MutableStateFlow(6) // months

    val uiState: StateFlow<CashFlowUiState> = _period.flatMapLatest { period ->
        // Create flows for each month's income and expenses
        val months = (0 until period).map { monthsAgo ->
            YearMonth.now().minusMonths(monthsAgo.toLong())
        }.reversed()

        // Build a list of per-month flows
        val monthlyFlows = months.map { month ->
            val start = month.atDay(1)
            val end = month.atEndOfMonth()
            combine(
                transactionRepository.getTotalIncome(start, end),
                transactionRepository.getTotalExpenses(start, end)
            ) { income, expenses ->
                MonthlyCashFlow(
                    month = month,
                    income = income,
                    expenses = expenses
                )
            }
        }

        // Combine all monthly flows into a single list flow
        if (monthlyFlows.isEmpty()) {
            flowOf(CashFlowUiState(isLoading = false))
        } else {
            combine(monthlyFlows) { monthlyDataArray ->
                val monthlyData = monthlyDataArray.toList()
                val totalIncome = monthlyData.sumOf { it.income }
                val totalExpenses = monthlyData.sumOf { it.expenses }
                val netCashFlow = totalIncome - totalExpenses

                CashFlowUiState(
                    totalIncome = totalIncome,
                    totalExpenses = totalExpenses,
                    netCashFlow = netCashFlow,
                    monthlyData = monthlyData,
                    periodLabel = "Last $period Months",
                    isLoading = false
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CashFlowUiState()
    )

    fun setPeriod(months: Int) {
        _period.value = months
    }
}
