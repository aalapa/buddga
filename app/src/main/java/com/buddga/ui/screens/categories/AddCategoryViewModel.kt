package com.buddga.ui.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buddga.domain.model.Category
import com.buddga.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    fun addCategory(
        name: String,
        color: Long,
        icon: String,
        groupName: String,
        type: com.buddga.domain.model.CategoryType
    ) {
        viewModelScope.launch {
            val category = Category(
                name = name,
                color = color,
                icon = icon,
                groupName = groupName,
                type = type
            )
            categoryRepository.addCategory(category)
        }
    }
}

