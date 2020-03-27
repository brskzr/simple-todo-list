package com.brskzr.todolist.converters

import androidx.room.TypeConverter
import com.brskzr.todolist.models.TodoItemType

class TodoItemTypeConverter{

    @TypeConverter
    fun fromTodoItemType(value: TodoItemType): Int = value.ordinal

    @TypeConverter
    fun toTodoItemType(value: Int): TodoItemType = when(value){
        1 -> TodoItemType.DO_IT_IMMEDIATE
        2 -> TodoItemType.PLAN_FOR_LATER
        3 -> TodoItemType.PASS_SOMEONE
        4 -> TodoItemType.NOTE_FOR_LATER
        else -> TodoItemType.NONE
    }

}