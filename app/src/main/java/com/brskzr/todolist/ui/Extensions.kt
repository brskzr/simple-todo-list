package com.brskzr.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


fun AppCompatActivity.container(containerId: Int, fragment: Fragment){
    this.supportFragmentManager.beginTransaction()
        .add(containerId, fragment)
        .commit()
}

fun Activity.getIntFromIntent(key:String):Int {
    return this.intent.extras?.let { it.getInt(key, 0) } ?: 0
}
