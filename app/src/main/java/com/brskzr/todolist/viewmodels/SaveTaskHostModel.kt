package com.brskzr.todolist.viewmodels

import android.app.Application
import android.text.format.DateUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.brskzr.todolist.data.AppDatabase
import com.brskzr.todolist.models.TodoItemDataModel
import com.brskzr.todolist.models.TodoItemType
import com.brskzr.todolist.notification.Alarm
import com.brskzr.todolist.ui.isToday
import com.brskzr.todolist.ui.toLocalDateTime
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SaveTaskHostViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var dataModel : MutableLiveData<TodoItemDataModel?>
    var selectedItemId: Int = 0
    var isUpdate:Boolean = false

    fun addNewItem(todoItemDataModel: TodoItemDataModel, success:()->Unit) {
        viewModelScope.launch {
            try {
                val db = AppDatabase(getApplication())
                    db.getTodoService().insert(todoItemDataModel)
                    //Eger bu gun icinse notification icin alarm setleniyor
                    setAlarmIfToday(todoItemDataModel)
                    success()

            }
            catch (ex: Exception){
                Log.e("MYAPPERROR", ex.toString())
            }
        }
    }

    private fun setAlarmIfToday(todoItemDataModel: TodoItemDataModel) {
        if (todoItemDataModel.type == TodoItemType.DO_IT_IMMEDIATE ||
            todoItemDataModel.type == TodoItemType.PLAN_FOR_LATER ||
            todoItemDataModel.type == TodoItemType.PASS_SOMEONE) {

            if (todoItemDataModel.remindAt.isToday()) {
                val hh = todoItemDataModel.remindAt.toLocalDateTime().hour
                val mm = todoItemDataModel.remindAt.toLocalDateTime().minute
                Alarm(getApplication()).setOneTime(hh, mm)
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