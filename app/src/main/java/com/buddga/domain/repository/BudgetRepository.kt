package com.buddga.domain.repository

import com.buddga.domain.model.Budget
import com.buddga.domain.model.BudgetWithCategory
import com.buddga.domain.model.Category
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.time.YearMonth

interface BudgetRepository {
    fun getBudgetsForMonth(month: YearMonth): Flow<List<BudgetWithCategory>>
    fun getTotalBudgetedForMonth(month: YearMonth): Flow<BigDecimal>
    suspend fun getBudgetByCategoryAndMonth(categoryId: Long, month: YearMonth): Budget?
    suspend fun addBudget(budget: Budget): Long
    suspend fun updateBudget(budget: Budget)
    suspend fun updateAllocated(id: Long, allocated: BigDecimal)
    suspend fun deleteBudget(categoryId: Long, month: YearMonth)
    suspend fun copyBudgetsFromPreviousMonth(currentMonth: YearMonth)
}

interface CategoryRepository {
    fun getAllCategories(): Flow<List<Category>>
    fun getCategoriesByGroup(groupName: String): Flow<List<Category>>
    fun getCategoryGroups(): Flow<List<String>>
    suspend fun getCategoryById(id: Long): Category?
    suspend fun addCategory(category: Category): Long
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(id: Long)
    suspend fun initializeDefaultCategories()
}
