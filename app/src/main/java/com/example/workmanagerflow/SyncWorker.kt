package com.example.workmanagerflow

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        const val DATA_USER_ID = "DATA_USER_ID"
    }

    override suspend fun doWork() = withContext(Dispatchers.IO) {
        val userId = inputData.getString(DATA_USER_ID)
        loadData(userId)
        return@withContext Result.success()
    }

    private suspend fun loadData(userId: String?) {
        Log.d("SyncWorker", "loadData(userId=$userId)")
        delay(3000)
    }
}