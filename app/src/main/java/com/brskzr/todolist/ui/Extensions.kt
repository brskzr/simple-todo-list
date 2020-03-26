package com.brskzr.todolist.ui

import android.app.Activity
import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


fun AppCompatActivity.container(containerId: Int, fragment: Fragment){
    this.supportFragmentManager.beginTransaction()
        .add(containerId, fragment)
        .commit()
}

fun Activity.getIntFromIntent(key:String):Int {
    return this.intent.extras?.let { it.getInt(key, 0) } ?: 0
}

fun LocalDateTime.toDate() : Date {
    return Date.from(this.atZone(ZoneId.systemDefault()).toInstant())
}

fun Fragment.toast(message: String) {
    Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
}

fun TextInputEditText.isEmpty(): Boolean {
    return this.text.toString().isEmpty()
}