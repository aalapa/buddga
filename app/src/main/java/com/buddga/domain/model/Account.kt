package com.buddga.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Account(
    val id: Long = 0,
    val name: String,
    val type: AccountType,
    val balance: BigDecimal,
    val color: Long,
    val icon: String,
    val isOnBudget: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

enum class AccountType {
    CHECKING,
    SAVINGS,
    CREDIT_CARD,
    CASH,
    INVESTMENT,
    OTHER
}
