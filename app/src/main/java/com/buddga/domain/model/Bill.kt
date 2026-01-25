package com.buddga.domain.model

import java.math.BigDecimal
import java.time.LocalDate

data class Bill(
    val id: Long = 0,
    val name: String,
    val amount: BigDecimal,
    val dueDate: LocalDate,
    val frequency: BillFrequency,
    val categoryId: Long,
    val accountId: Long? = null,
    val isPaid: Boolean = false,
    val isAutoPay: Boolean = false,
    val reminderDaysBefore: Int = 3,
    val notes: String = ""
)

enum class BillFrequency {
    ONCE,
    WEEKLY,
    BI_WEEKLY,
    MONTHLY,
    QUARTERLY,
    YEARLY
}

data class BillWithCategory(
    val bill: Bill,
    val categoryName: String,
    val categoryColor: Long
)

data class UpcomingBillOrIncome(
    val id: Long,
    val name: String,
    val amount: BigDecimal,
    val dueDate: LocalDate,
    val isIncome: Boolean,
    val categoryColor: Long
)
