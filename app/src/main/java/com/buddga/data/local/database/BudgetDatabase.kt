package com.buddga.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.buddga.data.local.database.dao.AccountDao
import com.buddga.data.local.database.dao.BillDao
import com.buddga.data.local.database.dao.BudgetDao
import com.buddga.data.local.database.dao.CategoryDao
import com.buddga.data.local.database.dao.TransactionDao
import com.buddga.data.local.database.entity.AccountEntity
import com.buddga.data.local.database.entity.BillEntity
import com.buddga.data.local.database.entity.BudgetEntity
import com.buddga.data.local.database.entity.CategoryEntity
import com.buddga.data.local.database.entity.TransactionEntity

@Database(
    entities = [
        AccountEntity::class,
        CategoryEntity::class,
        TransactionEntity::class,
        BudgetEntity::class,
        BillEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class BudgetDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun budgetDao(): BudgetDao
    abstract fun billDao(): BillDao
}
