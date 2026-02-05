package com.buddga.ui.screens.budgeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buddga.domain.model.BudgetWithCategory
import com.buddga.domain.model.CategorySpending
import com.buddga.domain.repository.AccountRepository
import com.buddga.domain.repository.BudgetRepository
import com.buddga.domain.repository.CategoryRepository
import com.buddga.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.YearMonth
import javax.inject.Inject

data class BudgetingUiState(
    val currentMonth: YearMonth = YearMonth.now(),
    val toBeBudgeted: BigDecimal = BigDecimal.ZERO,
    val totalBudgeted: BigDecimal = BigDecimal.ZERO,
    val totalSpent: BigDecimal = BigDecimal.ZERO,
    val categorySpending: List<CategorySpending> = emptyList(),
    val budgets: List<BudgetWithCategory> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class BudgetingViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _currentMonth = MutableStateFlow(YearMonth.now())
    val currentMonth: StateFlow<YearMonth> = _currentMonth.asStateFlow()

    val uiState: StateFlow<BudgetingUiState> = _currentMonth.flatMapLatest { month ->
        val startOfMonth = month.atDay(1)
        val endOfMonth = month.atEndOfMonth()

        combine(
            budgetRepository.getBudgetsForMonth(month),
            accountRepository.getTotalOnBudgetBalance(),
            budgetRepository.getTotalBudgetedForMonth(month),
            transactionRepository.getTotalExpenses(startOfMonth, endOfMonth)
        ) { budgets, totalBalance, totalBudgeted, monthExpenses ->

            val categorySpending = budgets.map { budgetWithCategory ->
                CategorySpending(
                    category = budgetWithCategory.category,
                    spent = budgetWithCategory.budget.spent,
                    budgeted = budgetWithCategory.budget.allocated,
                    available = budgetWithCategory.budget.allocated + budgetWithCategory.budget.carryover - budgetWithCategory.budget.spent
                )
            }

            val totalSpent = categorySpending.sumOf { it.spent }
            val toBeBudgeted = totalBalance - totalBudgeted

            BudgetingUiState(
                currentMonth = month,
                toBeBudgeted = toBeBudgeted,
                totalBudgeted = totalBudgeted,
                totalSpent = totalSpent,
                categorySpending = categorySpending,
                budgets = budgets,
                isLoading = false
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BudgetingUiState()
    )

    init {
        viewModelScope.launch {
            categoryRepository.initializeDefaultCategories()
        }
    }

    fun setMonth(month: YearMonth) {
        _currentMonth.value = month
    }

    fun previousMonth() {
        _currentMonth.value = _currentMonth.value.minusMonths(1)
    }

    fun nextMonth() {
        _currentMonth.value = _currentMonth.value.plusMonths(1)
    }
}
