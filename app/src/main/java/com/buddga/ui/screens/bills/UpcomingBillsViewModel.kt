package com.buddga.ui.screens.bills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buddga.domain.model.BillWithCategory
import com.buddga.domain.repository.AccountRepository
import com.buddga.domain.repository.BillRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject

data class UpcomingBillsUiState(
    val bills: List<BillWithCategory> = emptyList(),
    val totalUpcoming: BigDecimal = BigDecimal.ZERO,
    val cashBalance: BigDecimal = BigDecimal.ZERO,
    val projections: List<Pair<String, Float>> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class UpcomingBillsViewModel @Inject constructor(
    private val billRepository: BillRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    val uiState: StateFlow<UpcomingBillsUiState> = combine(
        billRepository.getUpcomingUnpaidBills(20),
        accountRepository.getTotalCashBalance(),
        billRepository.getTotalUpcomingBills(LocalDate.now(), LocalDate.now().plusDays(30))
    ) { bills, cashBalance, totalUpcoming ->
        // Build projection points from cash balance minus cumulative bills
        val sortedBills = bills.sortedBy { it.bill.dueDate }
        val projectionPoints = mutableListOf<Pair<String, Float>>()
        projectionPoints.add("1" to cashBalance.toFloat())

        // Create projections at intervals
        for (day in listOf(5, 10, 15, 20, 25, 30)) {
            val billsDueBefore = sortedBills.filter { it.bill.dueDate.dayOfMonth <= day }
            val totalDue = billsDueBefore.sumOf { it.bill.amount }
            val projected = cashBalance - totalDue
            projectionPoints.add(day.toString() to projected.toFloat())
        }

        UpcomingBillsUiState(
            bills = bills,
            totalUpcoming = totalUpcoming,
            cashBalance = cashBalance,
            projections = projectionPoints,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UpcomingBillsUiState()
    )

    fun markBillPaid(billId: Long) {
        viewModelScope.launch {
            billRepository.markAsPaid(billId, true)
        }
    }
}
