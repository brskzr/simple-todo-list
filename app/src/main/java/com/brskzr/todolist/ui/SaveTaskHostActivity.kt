package com.brskzr.todolist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.brskzr.todolist.R
import com.brskzr.todolist.models.Constants
import com.brskzr.todolist.models.TodoItemDataModel
import kotlinx.android.synthetic.main.activity_save_reminder.*
import kotlinx.android.synthetic.main.toolbar_savetask.*

class SaveTaskHostActivity : AppCompatActivity(), IDataHandler {

    private lateinit var eventHandler : ISaveTaskEventHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_task_host)
        initViews()
    }

    override fun onDataCreate(model: TodoItemDataModel){
        TODO("save task to db")
    }

    private fun initViews(){
        //TODO view model
        val taskType = getIntFromIntent(Constants.TASK_TYPE_KEY)
        val fragment = prepareScreen(taskType)
        eventHandler = fragment as ISaveTaskEventHandler
        container(R.id.host_frame, fragment)
    }

    private fun setToolbar(colorId: Int, stringId: Int){
        toolbar_savetask.background = resources.getDrawable(colorId, theme)
        tv_tb_header.setText(resources.getString(stringId))
        btn_tb_cancel.setOnClickListener { this.onBackPressed() }
        btn_save_task.setOnClickListener { this.eventHandler.onSave() }
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

interface IDataHandler{
    fun onDataCreate(model: TodoItemDataModel)
}
