package com.buddga.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.buddga.data.local.database.entity.BillEntity
import com.buddga.data.local.database.entity.BillWithCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {

    @Query("""
        SELECT b.id, b.name, b.amount, b.dueDate, b.frequency, b.categoryId,
               b.isPaid, b.isAutoPay, b.reminderDaysBefore,
               c.name as categoryName, c.color as categoryColor
        FROM bills b
        LEFT JOIN categories c ON b.categoryId = c.id
        ORDER BY b.dueDate ASC
    """)
    fun getAllBillsWithCategory(): Flow<List<BillWithCategoryEntity>>

    @Query("""
        SELECT b.id, b.name, b.amount, b.dueDate, b.frequency, b.categoryId,
               b.isPaid, b.isAutoPay, b.reminderDaysBefore,
               c.name as categoryName, c.color as categoryColor
        FROM bills b
        LEFT JOIN categories c ON b.categoryId = c.id
        WHERE b.dueDate >= :startDate AND b.dueDate <= :endDate
        ORDER BY b.dueDate ASC
    """)
    fun getBillsByDateRange(startDate: Long, endDate: Long): Flow<List<BillWithCategoryEntity>>

    @Query("""
        SELECT b.id, b.name, b.amount, b.dueDate, b.frequency, b.categoryId,
               b.isPaid, b.isAutoPay, b.reminderDaysBefore,
               c.name as categoryName, c.color as categoryColor
        FROM bills b
        LEFT JOIN categories c ON b.categoryId = c.id
        WHERE b.dueDate >= :today AND b.isPaid = 0
        ORDER BY b.dueDate ASC
        LIMIT :limit
    """)
    fun getUpcomingUnpaidBills(today: Long, limit: Int = 10): Flow<List<BillWithCategoryEntity>>

    @Query("""
        SELECT * FROM bills
        WHERE dueDate >= :startDate AND dueDate <= :endDate AND isPaid = 0
    """)
    suspend fun getBillsDueForReminder(startDate: Long, endDate: Long): List<BillEntity>

    @Query("SELECT * FROM bills WHERE id = :id")
    suspend fun getBillById(id: Long): BillEntity?

    @Query("SELECT SUM(CAST(amount AS REAL)) FROM bills WHERE dueDate >= :startDate AND dueDate <= :endDate AND isPaid = 0")
    fun getTotalUpcomingBills(startDate: Long, endDate: Long): Flow<Double?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bill: BillEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(bills: List<BillEntity>)

    @Update
    suspend fun update(bill: BillEntity)

    @Query("UPDATE bills SET isPaid = :isPaid WHERE id = :id")
    suspend fun updatePaidStatus(id: Long, isPaid: Boolean)

    @Delete
    suspend fun delete(bill: BillEntity)

    @Query("DELETE FROM bills WHERE id = :id")
    suspend fun deleteById(id: Long)
}
