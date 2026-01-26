package com.buddga

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.buddga.worker.RecurringTransactionWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class BudgetApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
        scheduleRecurringTransactionWorker()
    }
    
    private fun scheduleRecurringTransactionWorker() {
        val constraints = Constraints.Builder()
            .build()
            
        val recurringTransactionWork = PeriodicWorkRequestBuilder<RecurringTransactionWorker>(
            1, TimeUnit.DAYS // Run once per day
        )
            .setConstraints(constraints)
            .build()
            
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "recurring_transaction_check",
            ExistingPeriodicWorkPolicy.KEEP,
            recurringTransactionWork
        )
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    private fun createNotificationChannels() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Bill Reminders Channel
        val billChannel = NotificationChannel(
            CHANNEL_BILLS,
            getString(R.string.notification_channel_bills),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = getString(R.string.notification_channel_bills_desc)
        }

        // Balance Alerts Channel
        val balanceChannel = NotificationChannel(
            CHANNEL_BALANCE,
            getString(R.string.notification_channel_balance),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = getString(R.string.notification_channel_balance_desc)
        }

        notificationManager.createNotificationChannels(listOf(billChannel, balanceChannel))
    }

    companion object {
        const val CHANNEL_BILLS = "bill_reminders"
        const val CHANNEL_BALANCE = "balance_alerts"
    }
}
