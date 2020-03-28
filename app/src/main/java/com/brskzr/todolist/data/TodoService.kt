package com.brskzr.todolist.data

import androidx.room.*
import com.brskzr.todolist.models.TodoItemDataModel
import com.brskzr.todolist.models.TodoItemType

@Dao
interface TodoService {

    @Query("SELECT * FROM todos")
    suspend fun getAll(): List<TodoItemDataModel>

    @Query("SELECT * FROM todos WHERE Id = :Id")
    suspend fun getById(Id: Int): TodoItemDataModel

    @Query("SELECT * FROM todos WHERE type = :type ORDER BY remindAt desc")
    suspend fun getByType(type: TodoItemType): List<TodoItemDataModel>

    @Insert
    suspend fun insert(todo: TodoItemDataModel)

    @Delete
    suspend fun delete(todo: TodoItemDataModel)

    @Update
    suspend fun updateTodo(todos: TodoItemDataModel)
}