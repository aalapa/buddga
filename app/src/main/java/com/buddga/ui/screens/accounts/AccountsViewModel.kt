package com.buddga.ui.screens.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buddga.domain.model.Account
import com.buddga.domain.model.AccountType
import com.buddga.domain.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject

data class AccountsUiState(
    val accounts: List<Account> = emptyList(),
    val totalBalance: BigDecimal = BigDecimal.ZERO,
    val totalCashBalance: BigDecimal = BigDecimal.ZERO,
    val accountCount: Int = 0,
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)

    val uiState: StateFlow<AccountsUiState> = combine(
        accountRepository.getAllAccounts(),
        accountRepository.getTotalOnBudgetBalance(),
        accountRepository.getTotalCashBalance()
    ) { accounts, totalBalance, cashBalance ->
        AccountsUiState(
            accounts = accounts,
            totalBalance = totalBalance,
            totalCashBalance = cashBalance,
            accountCount = accounts.size,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AccountsUiState()
    )

    fun addAccount(
        name: String,
        type: AccountType,
        balance: BigDecimal,
        color: Long = 0xFF4CAF50,
        icon: String = "account_balance"
    ) {
        viewModelScope.launch {
            val account = Account(
                name = name,
                type = type,
                balance = balance,
                color = color,
                icon = icon,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
            accountRepository.addAccount(account)
        }
    }

    fun updateBalance(accountId: Long, newBalance: BigDecimal) {
        viewModelScope.launch {
            accountRepository.updateBalance(accountId, newBalance)
        }
    }

    fun deleteAccount(accountId: Long) {
        viewModelScope.launch {
            accountRepository.deleteAccount(accountId)
        }
    }
}
