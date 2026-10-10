package com.example.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.local.AppDatabase
import com.example.data.remote.VpnGateApi
import com.example.data.repository.SstpRepository

class SstpSyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d(TAG, "SstpSyncWorker triggered in background")
        val database = AppDatabase.getDatabase(applicationContext)
        val api = VpnGateApi.create()
        val repository = SstpRepository(database.sstpServerDao(), api, applicationContext)

        val result = repository.refreshServers()
        return if (result.isSuccess) {
            Log.i(TAG, "SstpSyncWorker finished successfully with ${result.getOrNull()} servers")
            Result.success()
        } else {
            Log.w(TAG, "SstpSyncWorker encountered error: ${result.exceptionOrNull()?.message}")
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }

    companion object {
        const val TAG = "SstpSyncWorker"
        const val PERIODIC_WORK_NAME = "sstp_periodic_sync"
        const val ONESHOT_WORK_NAME = "sstp_network_sync"
    }
}
