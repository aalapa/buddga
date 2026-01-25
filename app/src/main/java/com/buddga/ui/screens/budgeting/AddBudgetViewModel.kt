package com.buddga.ui.screens.budgeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buddga.domain.model.Budget
import com.buddga.domain.model.Category
import com.buddga.domain.repository.BudgetRepository
import com.buddga.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.YearMonth
import javax.inject.Inject

data class AddBudgetUiState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class AddBudgetViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val uiState: StateFlow<AddBudgetUiState> = categoryRepository.getAllCategories()
        .map { categories ->
            AddBudgetUiState(
                categories = categories.filter { it.name != "Income" }, // Exclude income category from budgeting
                isLoading = false
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AddBudgetUiState()
        )

    fun addBudget(
        categoryId: Long,
        month: YearMonth,
        allocated: BigDecimal
    ) {
        viewModelScope.launch {
            val budget = Budget(
                categoryId = categoryId,
                month = month,
                allocated = allocated
            )
            budgetRepository.addBudget(budget)
        }
    }
}
