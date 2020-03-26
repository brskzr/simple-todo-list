package com.brskzr.todolist.ui


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.brskzr.todolist.R
import com.brskzr.todolist.adapters.CheckListAdapterListener
import com.brskzr.todolist.adapters.ChecklistAdapter
import com.brskzr.todolist.models.ChecklistItem
import kotlinx.android.synthetic.main.fragment_plan_for_later.*
import java.time.LocalDateTime


class PlanForLaterFragment : Fragment(), SaveTaskHostActivity.ISaveTaskEventHandler {

    private lateinit var saveTaskHandler: IDataHandler
    private lateinit var checklistAdapter : ChecklistAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plan_for_later, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        saveTaskHandler = context as IDataHandler
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onSave() {
        if(!validate()) return



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
