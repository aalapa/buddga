package com.buddga.ui.screens.accounts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buddga.domain.model.Account
import com.buddga.domain.model.TransactionWithDetails
import com.buddga.domain.repository.AccountRepository
import com.buddga.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.math.BigDecimal
import javax.inject.Inject

data class AccountDetailUiState(
    val account: Account? = null,
    val transactions: List<TransactionWithDetails> = emptyList(),
    val clearedBalance: BigDecimal = BigDecimal.ZERO,
    val isLoading: Boolean = true
)

@HiltViewModel
class AccountDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val accountId: Long = savedStateHandle.get<Long>("accountId") ?: 0L

    val uiState: StateFlow<AccountDetailUiState> = combine(
        accountRepository.getAccountById(accountId),
        transactionRepository.getTransactionsByAccount(accountId)
    ) { account, transactions ->
        AccountDetailUiState(
            account = account,
            transactions = transactions,
            clearedBalance = account?.balance ?: BigDecimal.ZERO,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AccountDetailUiState()
    )
}
