package com.brskzr.todolist.ui

import android.app.Activity
import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
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

fun Activity.getBoolFromIntent(key:String):Boolean {
    return this.intent.extras?.let { it.getBoolean(key, false) } ?: false
}

fun LocalDateTime.toDate() : Date {
    return Date.from(this.atZone(ZoneId.systemDefault()).toInstant())
}

fun Fragment.toast(message: String) {
    Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun TextInputEditText.isEmpty(): Boolean {
    return this.text.toString().isEmpty()
}

fun EditText.isEmpty(): Boolean {
    return this.text.toString().isEmpty()
}

val PATTERN_DATE_TIME = "dd.MM.yyyy HH:mm"
val dateTimeFormat = SimpleDateFormat(PATTERN_DATE_TIME)

fun Date.formatDateTime() : String  = dateTimeFormat.format(this)

fun Date.toLocalDateTime() = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()