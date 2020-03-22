package com.brskzr.todolist.models

import androidx.room.*
import com.brskzr.todolist.converters.DateTypeConverter
import com.brskzr.todolist.converters.ListConverter
import com.brskzr.todolist.converters.TodoItemTypeConverter
import java.util.*

@Entity(tableName = "todos")
class TodoItemDataModel(
    @PrimaryKey(autoGenerate = true)
    val Id:Int,

    @ColumnInfo(name = "hasReminder")
    var hasReminder:Boolean,

    @TypeConverters(DateTypeConverter::class)
    var remindAt:Date,

    @ColumnInfo(name = "type")
    @TypeConverters(TodoItemTypeConverter::class)
    val type:TodoItemType,

    @ColumnInfo(name="tag")
    var tag:String,

    @ColumnInfo(name = "checklist")
    @TypeConverters(ListConverter::class)
    var checkList:List<ChecklistItem>
)


