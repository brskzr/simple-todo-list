package com.brskzr.todolist.notification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.brskzr.todolist.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.brskzr.todolist.models.Constants
import com.brskzr.todolist.ui.toLocalDateTime
import java.time.LocalDateTime
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if(isBootCompleted(intent)) {
            val hh = Date().toLocalDateTime().hour
            val mm = Date().toLocalDateTime().minute + 2
            Alarm(context!!).setRepeatedAt(hh,mm)
        }
        else{
            CoroutineScope(Dispatchers.IO).launch {
                val now = LocalDateTime.now()
                val nowLong = Alarm.getAlarmTimeInMillis(now.hour, now.minute)

                val from = nowLong.minus(1000 * 60)
                val to = nowLong.plus( 1000 * 60)

                val db = AppDatabase(context!!)
                val tasks = db.getTodoService().getByDateRange(from, to)

                if(tasks.any()) {
                    for(item in tasks){
                        NotificationUtils(context!!).notify(item.tag)
                    }
                }
            }
        }
    }

    private fun isBootCompleted(intent: Intent?) : Boolean = intent?.action == "android.intent.action.BOOT_COMPLETED" ?: false

}