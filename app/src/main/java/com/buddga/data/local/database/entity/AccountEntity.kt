package com.buddga.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: String,
    val balance: String, // Store as String to preserve precision
    val color: Long,
    val icon: String,
    val isOnBudget: Boolean = true,
    val createdAt: Long, // Epoch millis
    val updatedAt: Long
)
