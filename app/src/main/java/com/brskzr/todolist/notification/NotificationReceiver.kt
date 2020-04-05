package com.brskzr.todolist.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.brskzr.todolist.data.AppDatabase
import com.brskzr.todolist.models.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {

        intent?.extras?.let {
            val itemId = it.getInt(Constants.ITEM_ID, 0)

            if(itemId > 0) {
                CoroutineScope(Dispatchers.IO).launch {
                    val db = AppDatabase(context!!)
                    val task = db.getTodoService().getById(itemId)
                    NotificationUtils(context!!).notify(task.tag)
                }
            }
        }
    }
}