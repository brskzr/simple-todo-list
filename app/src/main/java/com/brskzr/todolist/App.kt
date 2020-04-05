package com.brskzr.todolist

import android.app.Application
import com.brskzr.todolist.notification.Alarm
import com.brskzr.todolist.notification.NotificationUtils

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        configure()
    }

    private fun configure() {
        NotificationUtils(this.applicationContext).createChannel()
        Alarm(this.applicationContext).setRepeatedAt(0,1)
    }
}