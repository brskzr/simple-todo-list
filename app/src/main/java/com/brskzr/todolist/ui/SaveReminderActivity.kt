package com.brskzr.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brskzr.todolist.R
import kotlinx.android.synthetic.main.activity_save_reminder.*


class SaveReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_reminder)

        val color = intent.extras?.getInt("toolbar_color") ?: 0

        btn_close_task.setOnClickListener { finish() }
    }
}
