package com.buddga.domain.repository

import com.buddga.domain.model.Account
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface AccountRepository {
    fun getAllAccounts(): Flow<List<Account>>
    fun getOnBudgetAccounts(): Flow<List<Account>>
    fun getAccountById(id: Long): Flow<Account?>
    fun getTotalOnBudgetBalance(): Flow<BigDecimal>
    fun getTotalCashBalance(): Flow<BigDecimal>
    suspend fun addAccount(account: Account): Long
    suspend fun updateAccount(account: Account)
    suspend fun updateBalance(id: Long, balance: BigDecimal)
    suspend fun deleteAccount(id: Long)
}
