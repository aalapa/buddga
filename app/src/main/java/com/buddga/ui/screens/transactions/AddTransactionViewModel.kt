package com.buddga.ui.screens.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buddga.domain.model.Account
import com.buddga.domain.model.Category
import com.buddga.domain.model.Transaction
import com.buddga.domain.model.TransactionType
import com.buddga.domain.repository.AccountRepository
import com.buddga.domain.repository.CategoryRepository
import com.buddga.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

data class AddTransactionUiState(
    val categories: List<Category> = emptyList(),
    val accounts: List<Account> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    val uiState: StateFlow<AddTransactionUiState> = combine(
        categoryRepository.getAllCategories(),
        accountRepository.getAllAccounts()
    ) { categories, accounts ->
        AddTransactionUiState(
            categories = categories,
            accounts = accounts,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AddTransactionUiState()
    )

    fun addTransaction(
        amount: BigDecimal,
        type: TransactionType,
        categoryId: Long,
        accountId: Long,
        payee: String,
        memo: String,
        date: LocalDate
    ) {
        viewModelScope.launch {
            val transaction = Transaction(
                amount = amount,
                type = type,
                categoryId = categoryId,
                accountId = accountId,
                payee = payee,
                memo = memo,
                date = date,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
            transactionRepository.addTransaction(transaction)
        }
    }
}
