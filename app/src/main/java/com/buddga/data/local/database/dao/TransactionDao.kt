package com.buddga.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.buddga.data.local.database.entity.TransactionEntity
import com.buddga.data.local.database.entity.TransactionWithDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("""
        SELECT t.id, t.amount, t.type, t.categoryId, t.accountId, t.payee, t.memo,
               t.date, t.isReconciled, t.isCleared, c.name as categoryName,
               c.color as categoryColor, a.name as accountName
        FROM transactions t
        LEFT JOIN categories c ON t.categoryId = c.id
        INNER JOIN accounts a ON t.accountId = a.id
        ORDER BY t.date DESC, t.id DESC
    """)
    fun getAllTransactionsWithDetails(): Flow<List<TransactionWithDetailsEntity>>

    @Query("""
        SELECT t.id, t.amount, t.type, t.categoryId, t.accountId, t.payee, t.memo,
               t.date, t.isReconciled, t.isCleared, c.name as categoryName,
               c.color as categoryColor, a.name as accountName
        FROM transactions t
        LEFT JOIN categories c ON t.categoryId = c.id
        INNER JOIN accounts a ON t.accountId = a.id
        WHERE t.accountId = :accountId
        ORDER BY t.date DESC, t.id DESC
    """)
    fun getTransactionsByAccount(accountId: Long): Flow<List<TransactionWithDetailsEntity>>

    @Query("""
        SELECT t.id, t.amount, t.type, t.categoryId, t.accountId, t.payee, t.memo,
               t.date, t.isReconciled, t.isCleared, c.name as categoryName,
               c.color as categoryColor, a.name as accountName
        FROM transactions t
        LEFT JOIN categories c ON t.categoryId = c.id
        INNER JOIN accounts a ON t.accountId = a.id
        WHERE t.date >= :startDate AND t.date <= :endDate
        ORDER BY t.date DESC, t.id DESC
    """)
    fun getTransactionsByDateRange(startDate: Long, endDate: Long): Flow<List<TransactionWithDetailsEntity>>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: Long): TransactionEntity?

    @Query("SELECT * FROM transactions WHERE accountId = :accountId")
    fun getTransactionsByAccountId(accountId: Long): Flow<List<TransactionEntity>>

    @Query("""
        SELECT SUM(CAST(amount AS REAL)) FROM transactions
        WHERE categoryId = :categoryId AND type = 'EXPENSE'
        AND date >= :startDate AND date <= :endDate
    """)
    fun getSpentByCategory(categoryId: Long, startDate: Long, endDate: Long): Flow<Double?>

    @Query("""
        SELECT SUM(CAST(amount AS REAL)) FROM transactions
        WHERE type = 'INCOME' AND date >= :startDate AND date <= :endDate
    """)
    fun getTotalIncome(startDate: Long, endDate: Long): Flow<Double?>

    @Query("""
        SELECT SUM(CAST(amount AS REAL)) FROM transactions
        WHERE type = 'EXPENSE' AND date >= :startDate AND date <= :endDate
    """)
    fun getTotalExpenses(startDate: Long, endDate: Long): Flow<Double?>

    @Query("""
        SELECT SUM(CASE WHEN type = 'INCOME' THEN CAST(amount AS REAL)
                        WHEN type = 'EXPENSE' THEN -CAST(amount AS REAL)
                        ELSE 0 END)
        FROM transactions WHERE date >= :startDate AND date <= :endDate
    """)
    fun getNetCashFlow(startDate: Long, endDate: Long): Flow<Double?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactions: List<TransactionEntity>)

    @Update
    suspend fun update(transaction: TransactionEntity)

    @Query("UPDATE transactions SET isReconciled = :isReconciled, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateReconciled(id: Long, isReconciled: Boolean, updatedAt: Long)

    @Delete
    suspend fun delete(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteById(id: Long)
}
