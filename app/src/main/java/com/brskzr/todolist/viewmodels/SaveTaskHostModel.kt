package com.brskzr.todolist.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.brskzr.todolist.data.AppDatabase
import com.brskzr.todolist.models.TodoItemDataModel
import kotlinx.coroutines.launch

class SaveTaskHostViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var dataModel : MutableLiveData<TodoItemDataModel?>
    var selectedItemId: Int = 0
    var isUpdate:Boolean = false

    fun addNewItem(todoItemDataModel: TodoItemDataModel, success:()->Unit) {
        viewModelScope.launch {
            try {
                val db = AppDatabase(getApplication())
                db.getTodoService().insert(todoItemDataModel)
                success()
            }
            catch (ex: Exception){
                Log.e("MYAPPERROR", ex.toString())
            }
        }
    }

    fun updateItem(todoItemDataModel: TodoItemDataModel, success:()->Unit) {
        viewModelScope.launch {
            try {
                val db = AppDatabase(getApplication())
                db.getTodoService().updateTodo(todoItemDataModel)
                success()
            }
            catch (ex: Exception){
                Log.e("MYAPPERROR", ex.toString())
            }
        }
    }

    fun getModel(id:Int) : LiveData<TodoItemDataModel?> {
        dataModel = MutableLiveData(null)
        viewModelScope.launch {
            try {
                val db = AppDatabase(getApplication())
                dataModel.value = db.getTodoService().getById(id)
            }
            catch (ex: Exception){
                Log.e("MYAPPERROR", ex.toString())
            }
        }

        return dataModel
    }
}