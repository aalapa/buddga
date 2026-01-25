package com.buddga.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.buddga.data.local.database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories WHERE isHidden = 0 ORDER BY groupName, sortOrder, name")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories ORDER BY groupName, sortOrder, name")
    fun getAllCategoriesIncludingHidden(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE groupName = :groupName AND isHidden = 0 ORDER BY sortOrder, name")
    fun getCategoriesByGroup(groupName: String): Flow<List<CategoryEntity>>

    @Query("SELECT DISTINCT groupName FROM categories WHERE isHidden = 0 ORDER BY groupName")
    fun getCategoryGroups(): Flow<List<String>>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Long): CategoryEntity?

    @Query("SELECT * FROM categories WHERE id = :id")
    fun getCategoryByIdFlow(id: Long): Flow<CategoryEntity?>

    @Query("SELECT * FROM categories WHERE name = :name LIMIT 1")
    suspend fun getCategoryByName(name: String): CategoryEntity?

    @Query("SELECT COUNT(*) FROM categories")
    suspend fun getCategoryCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: CategoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<CategoryEntity>)

    @Update
    suspend fun update(category: CategoryEntity)

    @Delete
    suspend fun delete(category: CategoryEntity)

    @Query("UPDATE categories SET isHidden = :isHidden WHERE id = :id")
    suspend fun setHidden(id: Long, isHidden: Boolean)
}
