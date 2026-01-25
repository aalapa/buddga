package com.buddga.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "budgets",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("categoryId"),
        Index(value = ["categoryId", "month"], unique = true)
    ]
)
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val categoryId: Long,
    val month: String, // Format: "2024-01"
    val allocated: String,
    val carryover: String = "0"
)

data class BudgetWithCategoryEntity(
    val id: Long,
    val categoryId: Long,
    val month: String,
    val allocated: String,
    val carryover: String,
    val categoryName: String,
    val categoryColor: Long,
    val categoryIcon: String,
    val categoryGroupName: String
)
