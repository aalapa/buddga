package com.buddga.domain.model

import java.math.BigDecimal
import java.time.YearMonth

data class Budget(
    val id: Long = 0,
    val categoryId: Long,
    val month: YearMonth,
    val allocated: BigDecimal,
    val spent: BigDecimal = BigDecimal.ZERO,
    val carryover: BigDecimal = BigDecimal.ZERO
) {
    val available: BigDecimal
        get() = allocated + carryover - spent

    val percentUsed: Float
        get() = if (allocated > BigDecimal.ZERO) {
            (spent.toFloat() / allocated.toFloat()).coerceIn(0f, 1f)
        } else 0f

    val isOverspent: Boolean
        get() = available < BigDecimal.ZERO
}

data class BudgetWithCategory(
    val budget: Budget,
    val category: Category
)

data class CategorySpending(
    val category: Category,
    val spent: BigDecimal,
    val budgeted: BigDecimal,
    val available: BigDecimal
) {
    val percentUsed: Float
        get() = if (budgeted > BigDecimal.ZERO) {
            (spent.toFloat() / budgeted.toFloat()).coerceIn(0f, 1f)
        } else 0f
}

data class MonthlyBudgetSummary(
    val month: YearMonth,
    val totalIncome: BigDecimal,
    val totalBudgeted: BigDecimal,
    val totalSpent: BigDecimal,
    val toBeBudgeted: BigDecimal,
    val categorySpending: List<CategorySpending>
)
