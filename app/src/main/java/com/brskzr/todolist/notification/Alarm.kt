package com.brskzr.todolist.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import com.brskzr.todolist.models.Constants
import com.brskzr.todolist.ui.toDate
import com.brskzr.todolist.ui.toLocalDateTime
import java.time.LocalDateTime
import java.util.*

class Alarm(val context:Context) {

    private val manager : AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private val HALF_MINUTE = 30 * 1000

    private val TWO_MINUTE: Long = 1000 * 60 * 2

    fun setOneTime(hour: Int, minute: Int) {
        val alarmTime = getAlarmTimeInMillis(hour, minute)
        manager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, getPendingIntent())
    }

    fun setRepeatedAt(hour: Int, minute: Int) {
        val alarmTime = getAlarmTimeInMillis(hour, minute)
        manager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            alarmTime + HALF_MINUTE ,
            TWO_MINUTE,
            getPendingIntent()
        )
    }

    fun setForNotification(hour: Int, minute: Int, intent:PendingIntent) {
        val alarmTime = getAlarmTimeInMillis(hour, minute)
        manager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, intent)
    }

    private fun getPendingIntent() : PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        return pendingIntent
    }

    companion object {
        fun getAlarmTimeInMillis(hour: Int, minute: Int) : Long {
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }
            return  calendar.timeInMillis
        }
    }

}