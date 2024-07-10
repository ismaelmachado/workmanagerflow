package com.example.workmanagerflow

import android.app.Application

class WorkApp : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(applicationContext)
    }
}