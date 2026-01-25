package com.buddga.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "bills",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("categoryId"),
        Index("dueDate")
    ]
)
data class BillEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val amount: String,
    val dueDate: Long, // Epoch day
    val frequency: String, // ONCE, WEEKLY, BI_WEEKLY, MONTHLY, QUARTERLY, YEARLY
    val categoryId: Long?,
    val accountId: Long? = null,
    val isPaid: Boolean = false,
    val isAutoPay: Boolean = false,
    val reminderDaysBefore: Int = 3,
    val notes: String = ""
)

data class BillWithCategoryEntity(
    val id: Long,
    val name: String,
    val amount: String,
    val dueDate: Long,
    val frequency: String,
    val categoryId: Long?,
    val isPaid: Boolean,
    val isAutoPay: Boolean,
    val reminderDaysBefore: Int,
    val categoryName: String?,
    val categoryColor: Long?
)
