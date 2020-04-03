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

    private var doItImmediates :  MutableLiveData<List<TodoItemDataModel>> = MutableLiveData<List<TodoItemDataModel>>(emptyList())
    val listOfdoItImmediate:LiveData<List<TodoItemDataModel>>
        get() {
            getDoItImmediates()
            return doItImmediates
        }

    private var planIt :  MutableLiveData<List<TodoItemDataModel>> = MutableLiveData<List<TodoItemDataModel>>(emptyList())
    val listOfPlanIt:LiveData<List<TodoItemDataModel>>
        get() {
            getPlanIts()
            return planIt
        }

    fun getNotes() {
        viewModelScope.launch {
            //todo bunu viewmodelin propu yap
            val service = AppDatabase.instance(getApplication()).getTodoService()
            val items = service.getByType(TodoItemType.NOTE_FOR_LATER)
            notes.value = items
        }
    }

    fun getPassSomeones() {
        viewModelScope.launch {
            val service = AppDatabase.instance(getApplication()).getTodoService()
            val items = service.getByType(TodoItemType.PASS_SOMEONE)
            passSomeones.value = items
        }
    }

    fun getDoItImmediates() {
        viewModelScope.launch {
            val service = AppDatabase.instance(getApplication()).getTodoService()
            val items = service.getByType(TodoItemType.DO_IT_IMMEDIATE)
            doItImmediates.value = items
        }
    }

    fun getPlanIts() {
        viewModelScope.launch {
            val service = AppDatabase.instance(getApplication()).getTodoService()
            val items = service.getByType(TodoItemType.PLAN_FOR_LATER)
            planIt.value = items
        }
    }

    fun deleteItem(todoItemDataModel: TodoItemDataModel) {
        viewModelScope.launch {
            try {
                val service = AppDatabase.instance(getApplication()).getTodoService()
                service.delete(todoItemDataModel)
                reloadData(todoItemDataModel.type)
            }
            catch (ex :Exception){

            }
        }
    }

    fun markAsCompleted(todoItemDataModel: TodoItemDataModel) {
        viewModelScope.launch {
            try {
                val service = AppDatabase.instance(getApplication()).getTodoService()
                todoItemDataModel.IsCompleted = true
                service.updateTodo(todoItemDataModel)
                reloadData(todoItemDataModel.type)
            }
            catch (ex :Exception){

            }
        }
    }

    private fun reloadData(type: TodoItemType) = when(type){
        TodoItemType.DO_IT_IMMEDIATE -> getDoItImmediates()
        TodoItemType.PLAN_FOR_LATER -> getPlanIts()
        TodoItemType.PASS_SOMEONE -> getPassSomeones()
        TodoItemType.NOTE_FOR_LATER -> getNotes()
        else -> {}
    }
}