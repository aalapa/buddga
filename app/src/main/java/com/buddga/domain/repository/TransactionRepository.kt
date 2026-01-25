package com.buddga.domain.repository

import com.buddga.domain.model.Transaction
import com.buddga.domain.model.TransactionWithDetails
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.time.LocalDate

interface TransactionRepository {
    fun getAllTransactions(): Flow<List<TransactionWithDetails>>
    fun getTransactionsByAccount(accountId: Long): Flow<List<TransactionWithDetails>>
    fun getTransactionsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<TransactionWithDetails>>
    fun getSpentByCategory(categoryId: Long, startDate: LocalDate, endDate: LocalDate): Flow<BigDecimal>
    fun getTotalIncome(startDate: LocalDate, endDate: LocalDate): Flow<BigDecimal>
    fun getTotalExpenses(startDate: LocalDate, endDate: LocalDate): Flow<BigDecimal>
    fun getNetCashFlow(startDate: LocalDate, endDate: LocalDate): Flow<BigDecimal>
    suspend fun getTransactionById(id: Long): Transaction?
    suspend fun addTransaction(transaction: Transaction): Long
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun setReconciled(id: Long, isReconciled: Boolean)
    suspend fun deleteTransaction(id: Long)
}
