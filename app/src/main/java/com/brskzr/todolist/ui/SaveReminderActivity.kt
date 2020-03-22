package com.brskzr.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.brskzr.todolist.R
import com.brskzr.todolist.adapters.CheckListAdapterListener
import com.brskzr.todolist.adapters.ChecklistAdapter
import com.brskzr.todolist.models.ChecklistItem
import kotlinx.android.synthetic.main.activity_save_reminder.*


class SaveReminderActivity : AppCompatActivity() {

    private lateinit var checklistAdapter : ChecklistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_reminder)
        initViews()
    }

    private fun initViews() {
        dtReminderDate.visibility = if (swHasReminder.isActivated) View.VISIBLE else View.GONE
        btn_close_task.setOnClickListener { finish() }
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
}
