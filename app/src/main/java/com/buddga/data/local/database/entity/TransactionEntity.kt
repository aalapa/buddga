package com.buddga.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("categoryId"),
        Index("accountId"),
        Index("date")
    ]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: String, // Store as String for precision
    val type: String, // INCOME, EXPENSE, TRANSFER
    val categoryId: Long?,
    val accountId: Long,
    val toAccountId: Long? = null, // For transfers
    val payee: String,
    val memo: String = "",
    val date: Long, // Epoch day
    val isReconciled: Boolean = false,
    val isCleared: Boolean = false,
    val isPending: Boolean = false,
    val isRecurring: Boolean = false,
    val recurrenceFrequency: String? = null, // WEEKLY, BIWEEKLY, MONTHLY, QUARTERLY, YEARLY
    val nextOccurrenceDate: Long? = null, // Epoch day
    val parentTransactionId: Long? = null,
    val createdAt: Long,
    val updatedAt: Long
)

data class TransactionWithDetailsEntity(
    val id: Long,
    val amount: String,
    val type: String,
    val categoryId: Long?,
    val accountId: Long,
    val payee: String,
    val memo: String,
    val date: Long,
    val isReconciled: Boolean,
    val isCleared: Boolean,
    val isPending: Boolean,
    val isRecurring: Boolean,
    val recurrenceFrequency: String?,
    val nextOccurrenceDate: Long?,
    val parentTransactionId: Long?,
    val categoryName: String?,
    val categoryColor: Long?,
    val accountName: String
)
