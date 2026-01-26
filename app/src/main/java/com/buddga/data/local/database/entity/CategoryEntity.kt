package com.buddga.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val color: Long,
    val icon: String,
    val groupName: String,
    val type: String = "EXPENSE", // "INCOME" or "EXPENSE"
    val sortOrder: Int = 0,
    val isHidden: Boolean = false
)
