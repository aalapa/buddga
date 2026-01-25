package com.buddga.di

import android.content.Context
import androidx.room.Room
import com.buddga.data.local.database.BudgetDatabase
import com.buddga.data.local.database.dao.AccountDao
import com.buddga.data.local.database.dao.BillDao
import com.buddga.data.local.database.dao.BudgetDao
import com.buddga.data.local.database.dao.CategoryDao
import com.buddga.data.local.database.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBudgetDatabase(@ApplicationContext context: Context): BudgetDatabase {
        return Room.databaseBuilder(
            context,
            BudgetDatabase::class.java,
            "budget_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideAccountDao(database: BudgetDatabase): AccountDao {
        return database.accountDao()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(database: BudgetDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(database: BudgetDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    @Singleton
    fun provideBudgetDao(database: BudgetDatabase): BudgetDao {
        return database.budgetDao()
    }

    @Provides
    @Singleton
    fun provideBillDao(database: BudgetDatabase): BillDao {
        return database.billDao()
    }
}
