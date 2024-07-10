package com.example.workmanagerflow

import android.content.Context

interface AppContainer {
    val workExecutor: WorkExecutor
}

class AppContainerImpl(context: Context) : AppContainer {
    override val workExecutor: WorkExecutor by lazy {
        WorkExecutorImpl(context)
    }
}