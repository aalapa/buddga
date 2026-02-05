package com.buddga.ui.screens.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buddga.domain.model.BillWithCategory
import com.buddga.domain.model.TransactionType
import com.buddga.domain.model.TransactionWithDetails
import com.buddga.domain.repository.AccountRepository
import com.buddga.domain.repository.BillRepository
import com.buddga.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class ForecastUiState(
    val currentBalance: BigDecimal = BigDecimal.ZERO,
    val projectedBalance: BigDecimal = BigDecimal.ZERO,
    val projections: List<Pair<String, Float>> = emptyList(),
    val upcomingBills: List<BillWithCategory> = emptyList(),
    val upcomingIncome: List<TransactionWithDetails> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class CashFlowForecastViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val billRepository: BillRepository
) : ViewModel() {

    private val dateFormatter = DateTimeFormatter.ofPattern("MMM d")

    val uiState: StateFlow<ForecastUiState> = combine(
        accountRepository.getTotalCashBalance(),
        billRepository.getUpcomingUnpaidBills(20),
        transactionRepository.getAllTransactions()
    ) { cashBalance, upcomingBills, allTransactions ->
        // Find recurring income transactions
        val recurringIncome = allTransactions.filter { txn ->
            txn.transaction.isRecurring &&
            txn.transaction.type == TransactionType.INCOME &&
            txn.transaction.nextOccurrenceDate != null &&
            txn.transaction.nextOccurrenceDate.isBefore(LocalDate.now().plusDays(31))
        }

        // Calculate projections over 4 weeks
        val today = LocalDate.now()
        var runningBalance = cashBalance
        val projectionPoints = mutableListOf<Pair<String, Float>>()
        projectionPoints.add("Now" to cashBalance.toFloat())

        for (week in 1..4) {
            val weekEnd = today.plusWeeks(week.toLong())
            val weekStart = today.plusWeeks((week - 1).toLong())

            // Subtract bills due this week
            val weekBills = upcomingBills.filter { billWithCat ->
                val dueDate = billWithCat.bill.dueDate
                !dueDate.isBefore(weekStart) && !dueDate.isAfter(weekEnd)
            }
            weekBills.forEach { runningBalance -= it.bill.amount }

            // Add recurring income due this week
            val weekIncome = recurringIncome.filter { txn ->
                val nextDate = txn.transaction.nextOccurrenceDate!!
                !nextDate.isBefore(weekStart) && !nextDate.isAfter(weekEnd)
            }
            weekIncome.forEach { runningBalance += it.transaction.amount }

            projectionPoints.add("Week $week" to runningBalance.toFloat())
        }

        val projectedBalance = runningBalance

        ForecastUiState(
            currentBalance = cashBalance,
            projectedBalance = projectedBalance,
            projections = projectionPoints,
            upcomingBills = upcomingBills,
            upcomingIncome = recurringIncome,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ForecastUiState()
    )
}
