package com.buddga.domain.model

enum class CategoryType {
    INCOME,
    EXPENSE
}

data class Category(
    val id: Long = 0,
    val name: String,
    val color: Long,
    val icon: String,
    val groupName: String,
    val type: CategoryType = CategoryType.EXPENSE,
    val sortOrder: Int = 0,
    val isHidden: Boolean = false
)

object DefaultCategories {
    val INCOME = Category(
        id = 1,
        name = "Income",
        color = 0xFF00BCD4,
        icon = "attach_money",
        groupName = "Income",
        type = CategoryType.INCOME
    )

    val GROCERIES = Category(
        id = 2,
        name = "Groceries",
        color = 0xFF4CAF50,
        icon = "shopping_cart",
        groupName = "Essentials",
        type = CategoryType.EXPENSE
    )

    val DINING = Category(
        id = 3,
        name = "Dining",
        color = 0xFFFF9800,
        icon = "restaurant",
        groupName = "Lifestyle",
        type = CategoryType.EXPENSE
    )

    val ENTERTAINMENT = Category(
        id = 4,
        name = "Entertainment",
        color = 0xFF2196F3,
        icon = "movie",
        groupName = "Lifestyle",
        type = CategoryType.EXPENSE
    )

    val TRANSPORT = Category(
        id = 5,
        name = "Transport",
        color = 0xFF9C27B0,
        icon = "directions_car",
        groupName = "Essentials",
        type = CategoryType.EXPENSE
    )

    val UTILITIES = Category(
        id = 6,
        name = "Utilities",
        color = 0xFF607D8B,
        icon = "bolt",
        groupName = "Bills",
        type = CategoryType.EXPENSE
    )

    val RENT = Category(
        id = 7,
        name = "Rent/Mortgage",
        color = 0xFFE91E63,
        icon = "home",
        groupName = "Bills",
        type = CategoryType.EXPENSE
    )

    val HEALTH = Category(
        id = 8,
        name = "Health",
        color = 0xFFF44336,
        icon = "medical_services",
        groupName = "Essentials",
        type = CategoryType.EXPENSE
    )

    val SHOPPING = Category(
        id = 9,
        name = "Shopping",
        color = 0xFFE91E63,
        icon = "shopping_bag",
        groupName = "Lifestyle",
        type = CategoryType.EXPENSE
    )

    val SAVINGS = Category(
        id = 10,
        name = "Savings",
        color = 0xFF009688,
        icon = "savings",
        groupName = "Goals",
        type = CategoryType.EXPENSE
    )

    val defaults = listOf(
        INCOME, GROCERIES, DINING, ENTERTAINMENT, TRANSPORT,
        UTILITIES, RENT, HEALTH, SHOPPING, SAVINGS
    )
}
