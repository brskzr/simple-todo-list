package com.brskzr.todolist.ui


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.brskzr.todolist.R
import com.brskzr.todolist.adapters.CheckListAdapterListener
import com.brskzr.todolist.adapters.ChecklistAdapter
import com.brskzr.todolist.models.ChecklistItem
import com.brskzr.todolist.models.TodoItemDataModel
import com.brskzr.todolist.models.TodoItemType
import com.brskzr.todolist.viewmodels.SaveTaskHostViewModel
import kotlinx.android.synthetic.main.fragment_do_it_immediate.*
import kotlinx.android.synthetic.main.fragment_plan_for_later.*
import kotlinx.android.synthetic.main.fragment_plan_for_later.et_tagname
import java.time.LocalDateTime
import android.app.Activity
import androidx.core.content.ContextCompat.getSystemService
import android.view.inputmethod.InputMethodManager


class PlanForLaterFragment : Fragment(), SaveTaskHostActivity.ISaveTaskEventHandler {

    private lateinit var checklistAdapter : ChecklistAdapter
    private lateinit var viewModel: SaveTaskHostViewModel
    private var model:TodoItemDataModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            viewModel = ViewModelProvider(it).get(SaveTaskHostViewModel::class.java)
        }

        checklistAdapter =  ChecklistAdapter(mutableListOf(), object: CheckListAdapterListener {
            override fun deleteOnClick(v: View, position: Int) {
                checklistAdapter.removeItem(position)
            }
        })

        if(viewModel.isUpdate){
            viewModel.getModel(viewModel.selectedItemId).observe(this, Observer {
                it?.let {
                    model = it
                    dtp_plandate.initialDate(it.remindAt.toLocalDateTime())
                    checklistAdapter.addRange(it.checkList.toMutableList())
                    et_tagname.setText(it.tag)
                }
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plan_for_later, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onSave() {
        if(!validate()) return

        if(viewModel.isUpdate){
            model?.apply {
                remindAt = dtp_plandate.selectedDate
                tag = et_tagname.text.toString()
                checkList = checklistAdapter.items
            }?.also {
                viewModel.updateItem(it, {
                    activity?.setResult(0, Intent())
                    activity?.finish()
                })
            }
        }
        else{
            val model = TodoItemDataModel(
                0,
                true,
                dtp_plandate.selectedDate,
                TodoItemType.PLAN_FOR_LATER,
                et_tagname.text.toString(),
                checklistAdapter.items,
                "",
                false
            )

            viewModel.addNewItem(model, {
                activity?.setResult(0, Intent())
                activity?.finish()
            })
        }
    }

    private fun initViews(){
        imgAddChecklistItem.setOnClickListener {
            if(et_checklist_item.isEmpty()){
                toast("What is cheklist item? Please enter..")
                return@setOnClickListener
            }
            if(checklistAdapter.items.size == 20) {
                toast("Max checklist count!")
                return@setOnClickListener
            }

            checklistAdapter.addItem(ChecklistItem(false, et_checklist_item.text.toString()))
            et_checklist_item.setText("")
            activity?.hideKeyboard()
        }

        rvChecklist.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)
        rvChecklist.adapter = checklistAdapter
        rvChecklist.setHasFixedSize(true)
    }

    private fun validate(): Boolean {

        if(et_tagname.isEmpty()){
            toast("What is the plan? Please enter...")
            return false
        }

        if(LocalDateTime.now().toDate() > dtp_plandate.selectedDate){
            toast("When will reminded ? Please choose correct time.")
            return false
        }

        return true
    }
}
