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

    private var data : MutableLiveData<List<TodoItemDataModel>> = MutableLiveData<List<TodoItemDataModel>>()
    val items: LiveData<List<TodoItemDataModel>>
    get() = data


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

    fun getItems() {
        viewModelScope.launch {
            val db = AppDatabase(getApplication())
            data.value = db.getTodoService().getAll()
        }
    }
}