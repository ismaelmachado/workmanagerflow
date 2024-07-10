package com.example.workmanagerflow

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.workmanagerflow.SyncWorker.Companion.DATA_USER_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface WorkExecutor {
    fun startWork(userId: String)
    suspend fun monitorWork(userId: String): Flow<WorkState>
}

class WorkExecutorImpl(
    private val context: Context
) : WorkExecutor {

    companion object {
        private const val WORK_TAG = "WORK_TAG"
    }

    override fun startWork(userId: String) {
        Log.d("WorkRepository", "startWork")
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val userIdData = Data.Builder()
            .putString(DATA_USER_ID, userId)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .setInputData(userIdData)
            .addTag(WORK_TAG)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }

    override suspend fun monitorWork(userId: String): Flow<WorkState> {
        Log.d("WorkRepository", "monitorWork")
        return WorkManager.getInstance(context)
            .getWorkInfosByTagFlow(WORK_TAG)
            .map { infoList ->
                infoList.map { info ->
                    Log.d("WorkRepository", "state ${info.id}=${info.state}")
                }
                WorkState.fromState(infoList.last().state)
            }
    }

}