package com.buddga.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.buddga.data.local.database.entity.BudgetEntity
import com.buddga.data.local.database.entity.BudgetWithCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Query("""
        SELECT b.id, b.categoryId, b.month, b.allocated, b.carryover,
               c.name as categoryName, c.color as categoryColor,
               c.icon as categoryIcon, c.groupName as categoryGroupName
        FROM budgets b
        INNER JOIN categories c ON b.categoryId = c.id
        WHERE b.month = :month
        ORDER BY c.groupName, c.sortOrder, c.name
    """)
    fun getBudgetsForMonth(month: String): Flow<List<BudgetWithCategoryEntity>>

    @Query("SELECT * FROM budgets WHERE month = :month")
    fun getBudgetEntitiesForMonth(month: String): Flow<List<BudgetEntity>>

    @Query("SELECT * FROM budgets WHERE categoryId = :categoryId AND month = :month")
    suspend fun getBudgetByCategoryAndMonth(categoryId: Long, month: String): BudgetEntity?

    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getBudgetById(id: Long): BudgetEntity?

    @Query("SELECT SUM(CAST(allocated AS REAL)) FROM budgets WHERE month = :month")
    fun getTotalBudgetedForMonth(month: String): Flow<Double?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: BudgetEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(budgets: List<BudgetEntity>)

    @Update
    suspend fun update(budget: BudgetEntity)

    @Query("UPDATE budgets SET allocated = :allocated WHERE id = :id")
    suspend fun updateAllocated(id: Long, allocated: String)

    @Query("UPDATE budgets SET carryover = :carryover WHERE id = :id")
    suspend fun updateCarryover(id: Long, carryover: String)

    @Delete
    suspend fun delete(budget: BudgetEntity)

    @Query("DELETE FROM budgets WHERE categoryId = :categoryId AND month = :month")
    suspend fun deleteByCategoryAndMonth(categoryId: Long, month: String)

    // Copy budgets from previous month
    @Query("""
        INSERT OR IGNORE INTO budgets (categoryId, month, allocated, carryover)
        SELECT categoryId, :newMonth, allocated, '0'
        FROM budgets WHERE month = :previousMonth
    """)
    suspend fun copyBudgetsFromPreviousMonth(previousMonth: String, newMonth: String)
}
