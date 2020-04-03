package com.brskzr.todolist.ui


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.brskzr.todolist.R
import com.brskzr.todolist.adapters.CheckListAdapterListener
import com.brskzr.todolist.adapters.ChecklistAdapter
import com.brskzr.todolist.models.ChecklistItem
import com.brskzr.todolist.models.TodoItemDataModel
import com.brskzr.todolist.models.TodoItemType
import com.brskzr.todolist.viewmodels.SaveTaskHostViewModel
import kotlinx.android.synthetic.main.fragment_plan_for_later.*
import java.time.LocalDateTime


class PlanForLaterFragment : Fragment(), SaveTaskHostActivity.ISaveTaskEventHandler {

    private lateinit var checklistAdapter : ChecklistAdapter
    private lateinit var viewModel: SaveTaskHostViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            viewModel = ViewModelProvider(it).get(SaveTaskHostViewModel::class.java)
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

    private fun initViews(){
        //Todo silmeyi adapter'in icinde hallet
        checklistAdapter =  ChecklistAdapter(mutableListOf(), object: CheckListAdapterListener {
            override fun deleteOnClick(v: View, position: Int) {
                checklistAdapter.removeItem(position)
            }
        })

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
