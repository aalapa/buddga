package com.buddga.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.buddga.data.local.database.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query("SELECT * FROM accounts ORDER BY name ASC")
    fun getAllAccounts(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts WHERE isOnBudget = 1 ORDER BY name ASC")
    fun getOnBudgetAccounts(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getAccountById(id: Long): AccountEntity?

    @Query("SELECT * FROM accounts WHERE id = :id")
    fun getAccountByIdFlow(id: Long): Flow<AccountEntity?>

    @Query("SELECT SUM(CAST(balance AS REAL)) FROM accounts WHERE isOnBudget = 1")
    fun getTotalOnBudgetBalance(): Flow<Double?>

    @Query("SELECT SUM(CAST(balance AS REAL)) FROM accounts WHERE type = 'CHECKING' OR type = 'SAVINGS' OR type = 'CASH'")
    fun getTotalCashBalance(): Flow<Double?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: AccountEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(accounts: List<AccountEntity>)

    @Update
    suspend fun update(account: AccountEntity)

    @Query("UPDATE accounts SET balance = :balance, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateBalance(id: Long, balance: String, updatedAt: Long)

    @Delete
    suspend fun delete(account: AccountEntity)

    @Query("DELETE FROM accounts WHERE id = :id")
    suspend fun deleteById(id: Long)
}
