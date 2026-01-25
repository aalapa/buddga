package com.buddga.data.repository

import com.buddga.data.local.database.dao.CategoryDao
import com.buddga.data.mapper.toDomain
import com.buddga.data.mapper.toEntity
import com.buddga.domain.model.Category
import com.buddga.domain.model.DefaultCategories
import com.buddga.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> =
        categoryDao.getAllCategories().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getCategoriesByGroup(groupName: String): Flow<List<Category>> =
        categoryDao.getCategoriesByGroup(groupName).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getCategoryGroups(): Flow<List<String>> =
        categoryDao.getCategoryGroups()

    override suspend fun getCategoryById(id: Long): Category? =
        categoryDao.getCategoryById(id)?.toDomain()

    override suspend fun addCategory(category: Category): Long =
        categoryDao.insert(category.toEntity())

    override suspend fun updateCategory(category: Category) =
        categoryDao.update(category.toEntity())

    override suspend fun deleteCategory(id: Long) {
        categoryDao.getCategoryById(id)?.let { entity ->
            categoryDao.delete(entity)
        }
    }

    override suspend fun initializeDefaultCategories() {
        val count = categoryDao.getCategoryCount()
        if (count == 0) {
            val entities = DefaultCategories.defaults.mapIndexed { index, category ->
                category.copy(sortOrder = index).toEntity()
            }
            categoryDao.insertAll(entities)
        }
    }
}
