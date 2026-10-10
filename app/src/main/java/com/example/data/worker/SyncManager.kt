package com.example.data.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class SyncManager(private val context: Context) {
    private val workManager = WorkManager.getInstance(context)

    fun schedulePeriodicSync(intervalMinutes: Long = 30) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val validInterval = intervalMinutes.coerceAtLeast(15) // WorkManager minimum periodic interval is 15 mins

        val syncRequest = PeriodicWorkRequestBuilder<SstpSyncWorker>(
            validInterval, TimeUnit.MINUTES
        ).setConstraints(constraints).build()

        workManager.enqueueUniquePeriodicWork(
            SstpSyncWorker.PERIODIC_WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            syncRequest
        )
    }

    fun triggerImmediateSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val oneTimeRequest = OneTimeWorkRequestBuilder<SstpSyncWorker>()
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(
            SstpSyncWorker.ONESHOT_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            oneTimeRequest
        )
    }

    fun cancelPeriodicSync() {
        workManager.cancelUniqueWork(SstpSyncWorker.PERIODIC_WORK_NAME)
    }
}
