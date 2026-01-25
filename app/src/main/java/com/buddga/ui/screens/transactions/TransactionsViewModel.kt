package com.buddga.ui.screens.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buddga.domain.model.Transaction
import com.buddga.domain.model.TransactionType
import com.buddga.domain.model.TransactionWithDetails
import com.buddga.domain.repository.AccountRepository
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
import java.time.YearMonth
import javax.inject.Inject

data class TransactionsUiState(
    val transactions: List<TransactionWithDetails> = emptyList(),
    val totalIncome: BigDecimal = BigDecimal.ZERO,
    val totalExpenses: BigDecimal = BigDecimal.ZERO,
    val selectedMonth: YearMonth = YearMonth.now(),
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _selectedMonth = MutableStateFlow(YearMonth.now())
    val selectedMonth: StateFlow<YearMonth> = _selectedMonth

    val uiState: StateFlow<TransactionsUiState> = combine(
        transactionRepository.getAllTransactions(),
        transactionRepository.getTotalIncome(
            YearMonth.now().atDay(1),
            YearMonth.now().atEndOfMonth()
        ),
        transactionRepository.getTotalExpenses(
            YearMonth.now().atDay(1),
            YearMonth.now().atEndOfMonth()
        ),
        _selectedMonth
    ) { transactions, income, expenses, month ->
        TransactionsUiState(
            transactions = transactions,
            totalIncome = income,
            totalExpenses = expenses,
            selectedMonth = month,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TransactionsUiState()
    )

    fun addTransaction(
        amount: BigDecimal,
        type: TransactionType,
        categoryId: Long,
        accountId: Long,
        payee: String,
        memo: String = "",
        date: LocalDate = LocalDate.now()
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

            // Update account balance
            val balanceChange = when (type) {
                TransactionType.INCOME -> amount
                TransactionType.EXPENSE -> amount.negate()
                TransactionType.TRANSFER -> BigDecimal.ZERO
            }
            // Note: In a full implementation, you'd get the current balance and add the change
        }
    }

    fun toggleReconciled(transactionId: Long, currentState: Boolean) {
        viewModelScope.launch {
            transactionRepository.setReconciled(transactionId, !currentState)
        }
    }

    fun deleteTransaction(transactionId: Long) {
        viewModelScope.launch {
            transactionRepository.deleteTransaction(transactionId)
        }
    }

    fun setMonth(month: YearMonth) {
        _selectedMonth.value = month
    }
}
