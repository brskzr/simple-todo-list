package com.brskzr.todolist.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.brskzr.todolist.models.TodoItemType
import com.brskzr.todolist.R
import com.brskzr.todolist.models.TodoItemDataModel
import kotlinx.android.synthetic.main.dialog_buttons.*

class DialogButtonsFragment():  DialogFragment(), View.OnClickListener{
    private lateinit var model: TodoItemDataModel
    private var isShareVisible:Boolean = false
    private var isCompleteVisible:Boolean = false
    private lateinit var action: IActionHandler

    override fun onClick(v: View?) {
        v?.let {
            when(v.id) {
                R.id.iv_complete -> action.onComplete(model)
                R.id.iv_edit -> action.onEdit(model)
                R.id.iv_remove -> action.onRemove(model)
                R.id.iv_share -> action.onShare(model)
            }
        }
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.brskzr.todolist.R.layout.dialog_buttons, container)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        divider_three.visibility = if(isShareVisible) View.VISIBLE else View.GONE
        iv_share.visibility = if(isShareVisible) View.VISIBLE else View.GONE

        divider_one.visibility = if(isCompleteVisible) View.VISIBLE else View.GONE
        iv_complete.visibility = if(isCompleteVisible) View.VISIBLE else View.GONE

        if(isCompleteVisible)
            iv_complete.setOnClickListener(this)
        if(isShareVisible)
            iv_share.setOnClickListener(this)

        iv_edit.setOnClickListener(this)
        iv_remove.setOnClickListener(this)
    }

    fun withActions(isShareVisible:Boolean = false, isCompleteVisible:Boolean = false) : DialogButtonsFragment {
        this.isShareVisible = isShareVisible
        this.isCompleteVisible = isCompleteVisible
        return this
    }

    fun open(context: Context, todoItemDataModel: TodoItemDataModel) {
        this.action= context as IActionHandler
        this.model = todoItemDataModel
        val activity = context as AppCompatActivity
        show(activity.supportFragmentManager,"dialog_buttons")
    }

    fun close(){
        dismiss()
    }

    interface IActionHandler {
        fun onComplete(todoItemDataModel: TodoItemDataModel)
        fun onEdit(todoItemDataModel: TodoItemDataModel)
        fun onRemove(todoItemDataModel: TodoItemDataModel)
        fun onShare(todoItemDataModel: TodoItemDataModel)
    }
}