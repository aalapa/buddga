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
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

enum class TransactionType {
    INCOME,
    EXPENSE,
    TRANSFER
}

data class TransactionWithDetails(
    val transaction: Transaction,
    val categoryName: String,
    val categoryColor: Long,
    val accountName: String
)
