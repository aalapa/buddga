package com.buddga

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BudgetApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
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
