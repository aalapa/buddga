package com.buddga.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.buddga.domain.model.RecurrenceFrequency
import com.buddga.domain.model.Transaction
import com.buddga.domain.repository.TransactionRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Worker that checks for recurring transactions and generates pending transactions
 * when their due date arrives.
 */
@HiltWorker
class RecurringTransactionWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val transactionRepository: TransactionRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val today = LocalDate.now()
            
            // Get all recurring transactions
            val allTransactions = transactionRepository.getAllTransactions().first()
            val recurringTransactions = allTransactions.filter { 
                it.transaction.isRecurring && 
                it.transaction.recurrenceFrequency != null && 
                it.transaction.nextOccurrenceDate != null &&
                !it.transaction.isPending
            }
            
            // Check each recurring transaction
            recurringTransactions.forEach { recurringTransaction ->
                val transaction = recurringTransaction.transaction
                val nextOccurrence = transaction.nextOccurrenceDate!!
                
                // If the next occurrence is today or in the past, create a pending transaction
                if (!nextOccurrence.isAfter(today)) {
                    createPendingTransaction(transaction, nextOccurrence)
                    
                    // Update the recurring transaction with the next occurrence date
                    val newNextOccurrence = calculateNextOccurrence(
                        nextOccurrence, 
                        transaction.recurrenceFrequency!!
                    )
                    
                    transactionRepository.updateTransaction(
                        transaction.copy(
                            nextOccurrenceDate = newNextOccurrence,
                            updatedAt = LocalDateTime.now()
                        )
                    )
                }
            }
            
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
    
    private suspend fun createPendingTransaction(
        transaction: Transaction,
        dueDate: LocalDate
    ) {
        val pendingTransaction = Transaction(
            amount = transaction.amount,
            type = transaction.type,
            categoryId = transaction.categoryId,
            accountId = transaction.accountId,
            payee = transaction.payee,
            memo = transaction.memo,
            date = dueDate,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            isPending = true,
            isRecurring = false,
            recurrenceFrequency = null,
            nextOccurrenceDate = null,
            parentTransactionId = transaction.id
        )
        
        transactionRepository.addTransaction(pendingTransaction)
    }
    
    private fun calculateNextOccurrence(date: LocalDate, frequency: RecurrenceFrequency): LocalDate {
        return when (frequency) {
            RecurrenceFrequency.WEEKLY -> date.plusWeeks(1)
            RecurrenceFrequency.BIWEEKLY -> date.plusWeeks(2)
            RecurrenceFrequency.MONTHLY -> date.plusMonths(1)
            RecurrenceFrequency.QUARTERLY -> date.plusMonths(3)
            RecurrenceFrequency.YEARLY -> date.plusYears(1)
        }
    }
}

