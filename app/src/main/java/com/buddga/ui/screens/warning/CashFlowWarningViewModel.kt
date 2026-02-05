package com.buddga.ui.screens.warning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buddga.domain.model.BillWithCategory
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
import javax.inject.Inject

data class Recommendation(
    val title: String,
    val description: String
)

data class WarningUiState(
    val hasWarning: Boolean = false,
    val warningTitle: String = "",
    val warningMessage: String = "",
    val isError: Boolean = false,
    val currentBalance: BigDecimal = BigDecimal.ZERO,
    val threshold: BigDecimal = BigDecimal("500"),
    val upcomingBills: List<BillWithCategory> = emptyList(),
    val recommendations: List<Recommendation> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class CashFlowWarningViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val billRepository: BillRepository
) : ViewModel() {

    private val safetyThreshold = BigDecimal("500")

    val uiState: StateFlow<WarningUiState> = combine(
        accountRepository.getTotalCashBalance(),
        billRepository.getUpcomingUnpaidBills(10),
        billRepository.getTotalUpcomingBills(LocalDate.now(), LocalDate.now().plusDays(30))
    ) { cashBalance, upcomingBills, totalUpcomingAmount ->
        val projectedBalance = cashBalance - totalUpcomingAmount
        val isNegative = projectedBalance < BigDecimal.ZERO
        val isBelowThreshold = projectedBalance < safetyThreshold
        val isLowNow = cashBalance < safetyThreshold

        val hasWarning = isNegative || isBelowThreshold || isLowNow
        val isError = isNegative || isLowNow

        val warningTitle = when {
            isNegative -> "Negative Balance Projected"
            isLowNow -> "Low Cash Balance"
            isBelowThreshold -> "Low Cash Forecast"
            else -> ""
        }

        val warningMessage = when {
            isNegative -> "Your projected balance will go negative after paying upcoming bills."
            isLowNow -> "Your current cash balance is below the safety threshold."
            isBelowThreshold -> "Your projected balance may fall below the safety threshold."
            else -> ""
        }

        val recommendations = mutableListOf<Recommendation>()
        if (hasWarning) {
            if (isNegative || isBelowThreshold) {
                recommendations.add(
                    Recommendation(
                        title = "Reduce spending",
                        description = "Consider reducing discretionary spending until your next paycheck."
                    )
                )
            }
            if (isNegative) {
                recommendations.add(
                    Recommendation(
                        title = "Move funds",
                        description = "Consider transferring funds from savings to cover upcoming bills."
                    )
                )
            }
            if (upcomingBills.isNotEmpty()) {
                recommendations.add(
                    Recommendation(
                        title = "Review upcoming bills",
                        description = "Check if any bills can be deferred or reduced to ease cash flow."
                    )
                )
            }
        }

        WarningUiState(
            hasWarning = hasWarning,
            warningTitle = warningTitle,
            warningMessage = warningMessage,
            isError = isError,
            currentBalance = cashBalance,
            threshold = safetyThreshold,
            upcomingBills = upcomingBills,
            recommendations = recommendations,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WarningUiState()
    )
}
