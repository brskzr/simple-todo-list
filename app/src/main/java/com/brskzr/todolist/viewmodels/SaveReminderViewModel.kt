package com.brskzr.todolist.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.brskzr.todolist.data.AppDatabase
import com.brskzr.todolist.models.TodoItemDataModel
import kotlinx.coroutines.launch

class SaveReminderViewModel(application: Application) : AndroidViewModel(application) {

    private var data : MutableLiveData<List<TodoItemDataModel>> = MutableLiveData<List<TodoItemDataModel>>()
    val items: LiveData<List<TodoItemDataModel>>
    get() = data


    fun addNewItem( todoItemDataModel: TodoItemDataModel) {
        viewModelScope.launch {
            val db = AppDatabase(getApplication())
            db.getTodoService().insert(todoItemDataModel)
            Log.i("HATA ALMADI", "haaaa")
        }
    }

    fun getItems() {
        viewModelScope.launch {
            val db = AppDatabase(getApplication())
            data.value = db.getTodoService().getAll()
        }
    }
}