package com.brskzr.todolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.brskzr.todolist.converters.DateTypeConverter
import com.brskzr.todolist.converters.ListConverter
import com.brskzr.todolist.converters.TodoItemTypeConverter
import com.brskzr.todolist.models.TodoItemDataModel

@Database(entities = arrayOf(TodoItemDataModel::class), version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class, TodoItemTypeConverter::class, ListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTodoService(): TodoService

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "todo-list.db")
            .build()
    }
}
