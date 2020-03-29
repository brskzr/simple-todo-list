package com.brskzr.todolist.converters

import androidx.room.TypeConverter
import com.brskzr.todolist.models.ChecklistItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList

class ListConverter {
    var gson = Gson()

    @TypeConverter
    fun fromStringToListTypeObject(data: String?): List<ChecklistItem> {
        if (data == null){
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<ChecklistItem>>() {}.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<ChecklistItem>?): String {
        return gson.toJson(someObjects)
    }
}