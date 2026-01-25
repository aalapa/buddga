package com.buddga.domain.model

import java.math.BigDecimal
import java.time.LocalDate
import java.time.YearMonth

data class CashFlowAnalysis(
    val period: String,
    val totalIncome: BigDecimal,
    val totalExpenses: BigDecimal,
    val netCashFlow: BigDecimal,
    val monthlyData: List<MonthlyCashFlow>
) {
    val isPositive: Boolean
        get() = netCashFlow >= BigDecimal.ZERO
}

data class MonthlyCashFlow(
    val month: YearMonth,
    val income: BigDecimal,
    val expenses: BigDecimal
) {
    val net: BigDecimal
        get() = income - expenses
}

data class WeeklyCashFlow(
    val weekStartDate: LocalDate,
    val weekEndDate: LocalDate,
    val income: BigDecimal,
    val expenses: BigDecimal,
    val dailyData: List<DailyCashFlow>
) {
    val net: BigDecimal
        get() = income - expenses
}

data class DailyCashFlow(
    val date: LocalDate,
    val income: BigDecimal,
    val expenses: BigDecimal
)

data class CashFlowForecast(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val startingBalance: BigDecimal,
    val projectedBalance: BigDecimal,
    val projectedIncome: BigDecimal,
    val projectedExpenses: BigDecimal,
    val dailyProjections: List<DailyProjection>,
    val upcomingBills: List<UpcomingBillOrIncome>,
    val upcomingIncome: List<UpcomingBillOrIncome>,
    val lowBalanceDate: LocalDate? = null,
    val isAtRisk: Boolean = false
)

data class DailyProjection(
    val date: LocalDate,
    val projectedBalance: BigDecimal,
    val scheduledIncome: BigDecimal,
    val scheduledExpenses: BigDecimal
)

data class CashFlowWarning(
    val type: WarningType,
    val message: String,
    val projectedBalance: BigDecimal,
    val riskDate: LocalDate?,
    val upcomingBills: List<UpcomingBillOrIncome>
)

enum class WarningType {
    LOW_BALANCE,
    NEGATIVE_BALANCE,
    OVERSPENDING,
    BILL_DUE_SOON
}
