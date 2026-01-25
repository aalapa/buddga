package com.buddga.ui.screens.cashflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buddga.domain.model.MonthlyCashFlow
import com.buddga.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.math.BigDecimal
import java.time.LocalDate
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

@HiltViewModel
class CashFlowViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _period = MutableStateFlow(6) // months

    val uiState: StateFlow<CashFlowUiState> = combine(
        transactionRepository.getTotalIncome(
            LocalDate.now().minusMonths(6),
            LocalDate.now()
        ),
        transactionRepository.getTotalExpenses(
            LocalDate.now().minusMonths(6),
            LocalDate.now()
        ),
        transactionRepository.getNetCashFlow(
            LocalDate.now().minusMonths(6),
            LocalDate.now()
        ),
        _period
    ) { income, expenses, netCashFlow, period ->
        // Generate monthly data for the chart
        val monthlyData = (0 until period).map { monthsAgo ->
            val month = YearMonth.now().minusMonths(monthsAgo.toLong())
            MonthlyCashFlow(
                month = month,
                income = BigDecimal.ZERO, // Would be fetched from repository
                expenses = BigDecimal.ZERO
            )
        }.reversed()

        CashFlowUiState(
            totalIncome = income,
            totalExpenses = expenses,
            netCashFlow = netCashFlow,
            monthlyData = monthlyData,
            periodLabel = "Last $period Months",
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CashFlowUiState()
    )

    fun setPeriod(months: Int) {
        _period.value = months
    }
}
