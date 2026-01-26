package com.buddga.data.mapper

import com.buddga.data.local.database.entity.AccountEntity
import com.buddga.data.local.database.entity.BillEntity
import com.buddga.data.local.database.entity.BillWithCategoryEntity
import com.buddga.data.local.database.entity.BudgetEntity
import com.buddga.data.local.database.entity.BudgetWithCategoryEntity
import com.buddga.data.local.database.entity.CategoryEntity
import com.buddga.data.local.database.entity.TransactionEntity
import com.buddga.data.local.database.entity.TransactionWithDetailsEntity
import com.buddga.domain.model.Account
import com.buddga.domain.model.AccountType
import com.buddga.domain.model.Bill
import com.buddga.domain.model.BillFrequency
import com.buddga.domain.model.BillWithCategory
import com.buddga.domain.model.Budget
import com.buddga.domain.model.BudgetWithCategory
import com.buddga.domain.model.Category
import com.buddga.domain.model.CategoryType
import com.buddga.domain.model.Transaction
import com.buddga.domain.model.TransactionType
import com.buddga.domain.model.TransactionWithDetails
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneId

// Account Mappers
fun AccountEntity.toDomain(): Account = Account(
    id = id,
    name = name,
    type = AccountType.valueOf(type),
    balance = BigDecimal(balance),
    color = color,
    icon = icon,
    isOnBudget = isOnBudget,
    createdAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(createdAt), ZoneId.systemDefault()),
    updatedAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(updatedAt), ZoneId.systemDefault())
)

fun Account.toEntity(): AccountEntity = AccountEntity(
    id = id,
    name = name,
    type = type.name,
    balance = balance.toPlainString(),
    color = color,
    icon = icon,
    isOnBudget = isOnBudget,
    createdAt = createdAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
    updatedAt = updatedAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
)

// Category Mappers
fun CategoryEntity.toDomain(): Category = Category(
    id = id,
    name = name,
    color = color,
    icon = icon,
    groupName = groupName,
    type = CategoryType.valueOf(type),
    sortOrder = sortOrder,
    isHidden = isHidden
)

fun Category.toEntity(): CategoryEntity = CategoryEntity(
    id = id,
    name = name,
    color = color,
    icon = icon,
    groupName = groupName,
    type = type.name,
    sortOrder = sortOrder,
    isHidden = isHidden
)

// Transaction Mappers
fun TransactionEntity.toDomain(): Transaction = Transaction(
    id = id,
    amount = BigDecimal(amount),
    type = TransactionType.valueOf(type),
    categoryId = categoryId ?: 0,
    accountId = accountId,
    toAccountId = toAccountId,
    payee = payee,
    memo = memo,
    date = LocalDate.ofEpochDay(date),
    isReconciled = isReconciled,
    isCleared = isCleared,
    isPending = isPending,
    isRecurring = isRecurring,
    recurrenceFrequency = recurrenceFrequency?.let { com.buddga.domain.model.RecurrenceFrequency.valueOf(it) },
    nextOccurrenceDate = nextOccurrenceDate?.let { LocalDate.ofEpochDay(it) },
    parentTransactionId = parentTransactionId,
    createdAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(createdAt), ZoneId.systemDefault()),
    updatedAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(updatedAt), ZoneId.systemDefault())
)

fun Transaction.toEntity(): TransactionEntity = TransactionEntity(
    id = id,
    amount = amount.toPlainString(),
    type = type.name,
    categoryId = if (categoryId == 0L) null else categoryId,
    accountId = accountId,
    toAccountId = toAccountId,
    payee = payee,
    memo = memo,
    date = date.toEpochDay(),
    isReconciled = isReconciled,
    isCleared = isCleared,
    isPending = isPending,
    isRecurring = isRecurring,
    recurrenceFrequency = recurrenceFrequency?.name,
    nextOccurrenceDate = nextOccurrenceDate?.toEpochDay(),
    parentTransactionId = parentTransactionId,
    createdAt = createdAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
    updatedAt = updatedAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
)

fun TransactionWithDetailsEntity.toDomain(): TransactionWithDetails = TransactionWithDetails(
    transaction = Transaction(
        id = id,
        amount = BigDecimal(amount),
        type = TransactionType.valueOf(type),
        categoryId = categoryId ?: 0,
        accountId = accountId,
        payee = payee,
        memo = memo,
        date = LocalDate.ofEpochDay(date),
        isReconciled = isReconciled,
        isCleared = isCleared,
        isPending = isPending,
        isRecurring = isRecurring,
        recurrenceFrequency = recurrenceFrequency?.let { com.buddga.domain.model.RecurrenceFrequency.valueOf(it) },
        nextOccurrenceDate = nextOccurrenceDate?.let { LocalDate.ofEpochDay(it) },
        parentTransactionId = parentTransactionId
    ),
    categoryName = categoryName ?: "Uncategorized",
    categoryColor = categoryColor ?: 0xFF607D8B,
    accountName = accountName
)

// Budget Mappers
fun BudgetEntity.toDomain(): Budget = Budget(
    id = id,
    categoryId = categoryId,
    month = YearMonth.parse(month),
    allocated = BigDecimal(allocated),
    carryover = BigDecimal(carryover)
)

fun Budget.toEntity(): BudgetEntity = BudgetEntity(
    id = id,
    categoryId = categoryId,
    month = month.toString(),
    allocated = allocated.toPlainString(),
    carryover = carryover.toPlainString()
)

fun BudgetWithCategoryEntity.toDomain(): BudgetWithCategory = BudgetWithCategory(
    budget = Budget(
        id = id,
        categoryId = categoryId,
        month = YearMonth.parse(month),
        allocated = BigDecimal(allocated),
        carryover = BigDecimal(carryover)
    ),
    category = Category(
        id = categoryId,
        name = categoryName,
        color = categoryColor,
        icon = categoryIcon,
        groupName = categoryGroupName,
        type = CategoryType.EXPENSE // Default to expense for existing data
    )
)

// Bill Mappers
fun BillEntity.toDomain(): Bill = Bill(
    id = id,
    name = name,
    amount = BigDecimal(amount),
    dueDate = LocalDate.ofEpochDay(dueDate),
    frequency = BillFrequency.valueOf(frequency),
    categoryId = categoryId ?: 0,
    accountId = accountId,
    isPaid = isPaid,
    isAutoPay = isAutoPay,
    reminderDaysBefore = reminderDaysBefore,
    notes = notes
)

fun Bill.toEntity(): BillEntity = BillEntity(
    id = id,
    name = name,
    amount = amount.toPlainString(),
    dueDate = dueDate.toEpochDay(),
    frequency = frequency.name,
    categoryId = if (categoryId == 0L) null else categoryId,
    accountId = accountId,
    isPaid = isPaid,
    isAutoPay = isAutoPay,
    reminderDaysBefore = reminderDaysBefore,
    notes = notes
)

fun BillWithCategoryEntity.toDomain(): BillWithCategory = BillWithCategory(
    bill = Bill(
        id = id,
        name = name,
        amount = BigDecimal(amount),
        dueDate = LocalDate.ofEpochDay(dueDate),
        frequency = BillFrequency.valueOf(frequency),
        categoryId = categoryId ?: 0,
        isPaid = isPaid,
        isAutoPay = isAutoPay,
        reminderDaysBefore = reminderDaysBefore
    ),
    categoryName = categoryName ?: "Uncategorized",
    categoryColor = categoryColor ?: 0xFF607D8B
)
