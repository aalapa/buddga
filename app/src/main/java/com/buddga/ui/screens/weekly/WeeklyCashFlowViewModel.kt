package com.buddga.ui.screens.weekly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buddga.domain.model.TransactionType
import com.buddga.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.math.BigDecimal
import java.time.YearMonth
import javax.inject.Inject

data class WeeklyUiState(
    val weeklyData: List<Pair<String, Pair<Float, Float>>> = emptyList(),
    val totalIncome: BigDecimal = BigDecimal.ZERO,
    val totalExpenses: BigDecimal = BigDecimal.ZERO,
    val netAmount: BigDecimal = BigDecimal.ZERO,
    val periodLabel: String = "This Month",
    val isLoading: Boolean = true
)

@HiltViewModel
class WeeklyCashFlowViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val uiState: StateFlow<WeeklyUiState> = run {
        val currentMonth = YearMonth.now()
        val startOfMonth = currentMonth.atDay(1)
        val endOfMonth = currentMonth.atEndOfMonth()

        combine(
            transactionRepository.getTransactionsByDateRange(startOfMonth, endOfMonth),
            transactionRepository.getTotalIncome(startOfMonth, endOfMonth),
            transactionRepository.getTotalExpenses(startOfMonth, endOfMonth)
        ) { transactions, totalIncome, totalExpenses ->
            // Group transactions by week of month
            val weekGroups = transactions.groupBy { txn ->
                val dayOfMonth = txn.transaction.date.dayOfMonth
                when {
                    dayOfMonth <= 7 -> 1
                    dayOfMonth <= 14 -> 2
                    dayOfMonth <= 21 -> 3
                    else -> 4
                }
            }

            val weeklyData = (1..4).map { week ->
                val weekTxns = weekGroups[week] ?: emptyList()
                val weekIncome = weekTxns
                    .filter { it.transaction.type == TransactionType.INCOME }
                    .sumOf { it.transaction.amount }
                    .toFloat()
                val weekExpense = weekTxns
                    .filter { it.transaction.type == TransactionType.EXPENSE }
                    .sumOf { it.transaction.amount }
                    .toFloat()
                "Wk $week" to (weekIncome to weekExpense)
            }

            WeeklyUiState(
                weeklyData = weeklyData,
                totalIncome = totalIncome,
                totalExpenses = totalExpenses,
                netAmount = totalIncome - totalExpenses,
                periodLabel = currentMonth.month.name.lowercase()
                    .replaceFirstChar { it.uppercase() } + " " + currentMonth.year,
                isLoading = false
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WeeklyUiState()
        )
    }
}
