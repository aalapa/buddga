package com.buddga.data.repository

import com.buddga.data.local.database.dao.TransactionDao
import com.buddga.data.mapper.toDomain
import com.buddga.data.mapper.toEntity
import com.buddga.domain.model.Transaction
import com.buddga.domain.model.TransactionWithDetails
import com.buddga.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {

    override fun getAllTransactions(): Flow<List<TransactionWithDetails>> =
        transactionDao.getAllTransactionsWithDetails().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getTransactionsByAccount(accountId: Long): Flow<List<TransactionWithDetails>> =
        transactionDao.getTransactionsByAccount(accountId).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getTransactionsByDateRange(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<TransactionWithDetails>> =
        transactionDao.getTransactionsByDateRange(
            startDate.toEpochDay(),
            endDate.toEpochDay()
        ).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getSpentByCategory(
        categoryId: Long,
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<BigDecimal> =
        transactionDao.getSpentByCategory(
            categoryId,
            startDate.toEpochDay(),
            endDate.toEpochDay()
        ).map { value ->
            BigDecimal(value ?: 0.0)
        }

    override fun getTotalIncome(startDate: LocalDate, endDate: LocalDate): Flow<BigDecimal> =
        transactionDao.getTotalIncome(
            startDate.toEpochDay(),
            endDate.toEpochDay()
        ).map { value ->
            BigDecimal(value ?: 0.0)
        }

    override fun getTotalExpenses(startDate: LocalDate, endDate: LocalDate): Flow<BigDecimal> =
        transactionDao.getTotalExpenses(
            startDate.toEpochDay(),
            endDate.toEpochDay()
        ).map { value ->
            BigDecimal(value ?: 0.0)
        }

    override fun getNetCashFlow(startDate: LocalDate, endDate: LocalDate): Flow<BigDecimal> =
        transactionDao.getNetCashFlow(
            startDate.toEpochDay(),
            endDate.toEpochDay()
        ).map { value ->
            BigDecimal(value ?: 0.0)
        }

    override suspend fun getTransactionById(id: Long): Transaction? =
        transactionDao.getTransactionById(id)?.toDomain()

    override suspend fun addTransaction(transaction: Transaction): Long =
        transactionDao.insert(transaction.toEntity())

    override suspend fun updateTransaction(transaction: Transaction) =
        transactionDao.update(transaction.copy(updatedAt = LocalDateTime.now()).toEntity())

    override suspend fun setReconciled(id: Long, isReconciled: Boolean) {
        val updatedAt = LocalDateTime.now()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        transactionDao.updateReconciled(id, isReconciled, updatedAt)
    }

    override suspend fun deleteTransaction(id: Long) =
        transactionDao.deleteById(id)
}
