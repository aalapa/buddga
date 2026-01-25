package com.buddga.data.repository

import com.buddga.data.local.database.dao.BudgetDao
import com.buddga.data.mapper.toDomain
import com.buddga.data.mapper.toEntity
import com.buddga.domain.model.Budget
import com.buddga.domain.model.BudgetWithCategory
import com.buddga.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import java.time.YearMonth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetRepositoryImpl @Inject constructor(
    private val budgetDao: BudgetDao
) : BudgetRepository {

    override fun getBudgetsForMonth(month: YearMonth): Flow<List<BudgetWithCategory>> =
        budgetDao.getBudgetsForMonth(month.toString()).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getTotalBudgetedForMonth(month: YearMonth): Flow<BigDecimal> =
        budgetDao.getTotalBudgetedForMonth(month.toString()).map { value ->
            BigDecimal(value ?: 0.0)
        }

    override suspend fun getBudgetByCategoryAndMonth(categoryId: Long, month: YearMonth): Budget? =
        budgetDao.getBudgetByCategoryAndMonth(categoryId, month.toString())?.toDomain()

    override suspend fun addBudget(budget: Budget): Long =
        budgetDao.insert(budget.toEntity())

    override suspend fun updateBudget(budget: Budget) =
        budgetDao.update(budget.toEntity())

    override suspend fun updateAllocated(id: Long, allocated: BigDecimal) =
        budgetDao.updateAllocated(id, allocated.toPlainString())

    override suspend fun deleteBudget(categoryId: Long, month: YearMonth) =
        budgetDao.deleteByCategoryAndMonth(categoryId, month.toString())

    override suspend fun copyBudgetsFromPreviousMonth(currentMonth: YearMonth) {
        val previousMonth = currentMonth.minusMonths(1)
        budgetDao.copyBudgetsFromPreviousMonth(
            previousMonth.toString(),
            currentMonth.toString()
        )
    }
}
