package com.example.workmanagerflow

import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkInfo.State.BLOCKED
import androidx.work.WorkInfo.State.CANCELLED
import androidx.work.WorkInfo.State.ENQUEUED
import androidx.work.WorkInfo.State.FAILED
import androidx.work.WorkInfo.State.RUNNING
import androidx.work.WorkInfo.State.SUCCEEDED
import androidx.work.WorkRequest

sealed class WorkState {

    companion object {

        fun fromState(state: WorkInfo.State) = when (state) {
            ENQUEUED -> Enqueued
            RUNNING -> Running
            SUCCEEDED -> Succeeded
            FAILED -> Failed
            BLOCKED -> Blocked
            CANCELLED -> Cancelled
        }

    }

    /**
     * Returns `true` if this State is considered finished:
     * [.SUCCEEDED], [.FAILED], and * [.CANCELLED]
     */
    val isFinished: Boolean
        get() = this is Succeeded || this is Failed || this is Cancelled

    /**
     * Used to indicate that the [WorkRequest] is enqueued and eligible to run when its
     * [Constraints] are met and resources are available.
     */
    data object Enqueued : WorkState()

    /**
     * Used to indicate that the [WorkRequest] is currently being executed.
     */
    data object Running : WorkState()

    /**
     * Used to indicate that the [WorkRequest] has completed in a successful state.  Note
     * that [PeriodicWorkRequest]s will never enter this state (they will simply go back
     * to [.ENQUEUED] and be eligible to run again).
     */
    data object Succeeded : WorkState()

    /**
     * Used to indicate that the [WorkRequest] has completed in a failure state.  All
     * dependent work will also be marked as `#FAILED` and will never run.
     */
    data object Failed : WorkState()

    /**
     * Used to indicate that the [WorkRequest] is currently blocked because its
     * prerequisites haven't finished successfully.
     */
    data object Blocked : WorkState()

    /**
     * Used to indicate that the [WorkRequest] has been cancelled and will not execute.
     * All dependent work will also be marked as `#CANCELLED` and will not run.
     */
    data object Cancelled : WorkState()
}