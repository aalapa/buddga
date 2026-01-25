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
    val categoryName: String?,
    val categoryColor: Long?,
    val accountName: String
)
