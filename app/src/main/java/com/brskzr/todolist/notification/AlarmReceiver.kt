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
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if(isBootCompleted(intent)) {
            val hh = Date().toLocalDateTime().hour
            val mm = Date().toLocalDateTime().minute + 5
            Alarm(context!!).setOneTime(hh, mm)
            Alarm(context!!).setRepeatedAt(0,1)
        }
        else{
            CoroutineScope(Dispatchers.IO).launch {
                val from = Alarm.getAlarmTimeInMillis(0, 0)
                val to = Alarm.getAlarmTimeInMillis(23, 59)

                val db = AppDatabase(context!!)
                val tasks = db.getTodoService().getByDateRange(from, to)

                if(tasks.any()) {
                    val alarm = Alarm(context)
                    for(item in tasks){
                        val intent = Intent(context, NotificationReceiver::class.java)
                        intent.putExtra(Constants.ITEM_ID, item.Id)
                        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
                        val hh = Date().toLocalDateTime().hour
                        val mm = Date().toLocalDateTime().minute
                        alarm.setForNotification(hh, mm, pendingIntent)
                    }
                }
            }
        }
    }

    private fun isBootCompleted(intent: Intent?) : Boolean = intent?.action == "android.intent.action.BOOT_COMPLETED" ?: false

}