package com.brskzr.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.brskzr.todolist.R
import com.brskzr.todolist.models.Constants
import com.brskzr.todolist.models.TodoItemDataModel
import com.brskzr.todolist.viewmodels.SaveTaskHostViewModel
import kotlinx.android.synthetic.main.activity_save_reminder.*
import kotlinx.android.synthetic.main.toolbar_savetask.*

class SaveTaskHostActivity : AppCompatActivity() {

    private lateinit var viewModel:SaveTaskHostViewModel
    private lateinit var eventHandler : ISaveTaskEventHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_task_host)
        initViews()
    }

    private fun initViews(){
        viewModel = ViewModelProvider(this).get(SaveTaskHostViewModel::class.java)

        val taskType = getIntFromIntent(Constants.TASK_TYPE_KEY)
        val fragment = prepareScreen(taskType)
        eventHandler = fragment as ISaveTaskEventHandler

        val isUpdate = getBoolFromIntent(Constants.IS_UPDATE)
        if(isUpdate) {
            viewModel.selectedItemId = getIntFromIntent(Constants.ITEM_ID)
            viewModel.isUpdate = isUpdate
        }

        container(R.id.host_frame, fragment)
    }

    private fun setToolbar(colorId: Int, stringId: Int){
        toolbar_savetask.background = resources.getDrawable(colorId, theme)
        tv_tb_header.setText(resources.getString(stringId))
        btn_tb_cancel.setOnClickListener { this.onBackPressed() }
        btn_tb_approve.setOnClickListener { this.eventHandler.onSave() }
    }

    private fun prepareScreen(taskType: Int): Fragment = when(taskType) {
        0 -> {
            setToolbar(R.color.colorLabelGreen, R.string.do_it_immediate)
            DoItImmediateFragment()
        }
        1 -> {
            setToolbar(R.color.colorLabelBlue, R.string.plan_for_later)
            PlanForLaterFragment()
        }
        2 -> {
            setToolbar(R.color.colorLabelYellow, R.string.pass_someone)
            PassSomeoneFragment()
        }
        3 -> {
            setToolbar(R.color.colorLabelRed, R.string.note_for_later)
            NoteForLaterFragment()
        }
        else -> NoteForLaterFragment()
    }

    interface ISaveTaskEventHandler {
        fun onSave()
    }
}
