package com.buddga.ui.screens.bills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buddga.domain.model.Bill
import com.buddga.domain.model.BillFrequency
import com.buddga.domain.model.Category
import com.buddga.domain.repository.BillRepository
import com.buddga.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject

data class AddBillUiState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class AddBillViewModel @Inject constructor(
    private val billRepository: BillRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val uiState: StateFlow<AddBillUiState> = categoryRepository.getAllCategories()
        .map { categories ->
            AddBillUiState(
                categories = categories,
                isLoading = false
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AddBillUiState()
        )

    fun addBill(
        name: String,
        amount: BigDecimal,
        categoryId: Long,
        frequency: BillFrequency,
        dueDate: LocalDate,
        isAutoPay: Boolean,
        reminderDays: Int,
        notes: String
    ) {
        viewModelScope.launch {
            val bill = Bill(
                name = name,
                amount = amount,
                categoryId = categoryId,
                frequency = frequency,
                dueDate = dueDate,
                isAutoPay = isAutoPay,
                reminderDaysBefore = reminderDays,
                notes = notes
            )
            billRepository.addBill(bill)
        }
    }
}
