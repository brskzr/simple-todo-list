package com.brskzr.todolist

import android.app.Application
import com.brskzr.todolist.notification.Alarm
import com.brskzr.todolist.notification.NotificationUtils
import com.brskzr.todolist.ui.toLocalDateTime
import java.util.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        configure()
    }

    private fun configure() {
        NotificationUtils(this.applicationContext).createChannel()

        val hh = Date().toLocalDateTime().hour
        val mm = Date().toLocalDateTime().minute + 2
        Alarm(this.applicationContext).setRepeatedAt(hh,mm)
    }
}