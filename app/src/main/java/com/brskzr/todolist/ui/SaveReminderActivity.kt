package com.brskzr.todolist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.brskzr.todolist.R
import com.brskzr.todolist.adapters.CheckListAdapterListener
import com.brskzr.todolist.adapters.ChecklistAdapter
import com.brskzr.todolist.data.AppDatabase
import com.brskzr.todolist.models.ChecklistItem
import com.brskzr.todolist.models.TodoItemDataModel
import com.brskzr.todolist.models.TodoItemType
import com.brskzr.todolist.viewmodels.SaveReminderViewModel
import kotlinx.android.synthetic.main.activity_save_reminder.*



class SaveReminderActivity : AppCompatActivity() {
    private lateinit var checklistAdapter : ChecklistAdapter
    private lateinit var viewModel:SaveReminderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_reminder)
        initViews()
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this).get(SaveReminderViewModel::class.java)

        /*viewModel.items.observe(this, Observer { items ->
            for(i in items)
            {
                Log.i("DATAGELDI", i.tag)
            }
        })*/

        dtReminderDate.visibility = if (swHasReminder.isActivated) View.VISIBLE else View.GONE
        btn_close_task.setOnClickListener { finish() }
        btn_save_task.setOnClickListener { createNewTask() }

        swHasReminder.setOnCheckedChangeListener { buttonView, isChecked ->
            dtReminderDate.visibility =  if (isChecked) View.VISIBLE else View.GONE
        }

        checklistAdapter =  ChecklistAdapter(mutableListOf(), object: CheckListAdapterListener {
            override fun deleteOnClick(v: View, position: Int) {
                checklistAdapter.removeItem(position)
            }
        })

        imgAddChecklistItem.setOnClickListener {
            checklistAdapter.addItem(ChecklistItem(false, etItemTagName.text.toString()))
            etItemTagName.setText("")
        }

        rvChecklist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvChecklist.adapter = checklistAdapter
        rvChecklist.setHasFixedSize(true)
    }

    private fun createNewTask(){
        val data = TodoItemDataModel(0,
            swHasReminder.isChecked,
            dtReminderDate.selectedDate,
            TodoItemType.PLAN_IT,
            etItemTagName.text.toString(),
            checklistAdapter.items.toList())

        viewModel.addNewItem(data)
    }
}
