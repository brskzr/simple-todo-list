package com.brskzr.todolist.converters

import androidx.room.TypeConverter
import com.brskzr.todolist.models.TodoItemType

class TodoItemTypeConverter{

    @TypeConverter
    fun fromTodoItemType(value: TodoItemType): Int = value.ordinal

    @TypeConverter
    fun toTodoItemType(value: Int): TodoItemType = when(value){
        1 -> TodoItemType.DO_IT_NOW
        2 -> TodoItemType.PLAN_IT
        3 -> TodoItemType.DELEAGATE
        4 -> TodoItemType.NOT_IMPORTANT
        else -> TodoItemType.NONE
    }

}