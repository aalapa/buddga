package com.buddga.domain.model

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

data class Transaction(
    val id: Long = 0,
    val amount: BigDecimal,
    val type: TransactionType,
    val categoryId: Long,
    val accountId: Long,
    val toAccountId: Long? = null, // For transfers
    val payee: String,
    val memo: String = "",
    val date: LocalDate,
    val isReconciled: Boolean = false,
    val isCleared: Boolean = false,
    val isPending: Boolean = false, // For pending/needs approval transactions
    val isRecurring: Boolean = false,
    val recurrenceFrequency: RecurrenceFrequency? = null,
    val nextOccurrenceDate: LocalDate? = null,
    val parentTransactionId: Long? = null, // Links back to recurring template
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

enum class TransactionType {
    INCOME,
    EXPENSE,
    TRANSFER
}

enum class RecurrenceFrequency {
    WEEKLY,
    BIWEEKLY,
    MONTHLY,
    QUARTERLY,
    YEARLY
}

data class TransactionWithDetails(
    val transaction: Transaction,
    val categoryName: String,
    val categoryColor: Long,
    val accountName: String
)
