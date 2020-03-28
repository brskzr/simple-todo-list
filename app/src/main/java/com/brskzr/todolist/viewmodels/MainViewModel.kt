package com.brskzr.todolist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.brskzr.todolist.data.AppDatabase
import com.brskzr.todolist.models.TodoItemDataModel
import com.brskzr.todolist.models.TodoItemType
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var notes : MutableLiveData<List<TodoItemDataModel>> = MutableLiveData<List<TodoItemDataModel>>(emptyList())
    val listOfNote:LiveData<List<TodoItemDataModel>>
        get() {
            getNotes()
            return notes
        }

    private var passSomeones : MutableLiveData<List<TodoItemDataModel>> = MutableLiveData<List<TodoItemDataModel>>(emptyList())
    val listPassSomone:LiveData<List<TodoItemDataModel>>
        get() {
            getPassSomeones()
            return passSomeones
        }

    fun getNotes() {
        viewModelScope.launch {
            val service = AppDatabase.instance(getApplication()).getTodoService()
            val items = service.getByType(TodoItemType.NOTE_FOR_LATER)
            notes.postValue(items)
        }
    }

    fun getPassSomeones() {
        viewModelScope.launch {
            val service = AppDatabase.instance(getApplication()).getTodoService()
            val items = service.getByType(TodoItemType.PASS_SOMEONE)
            passSomeones.postValue(items)
        }
    }
}