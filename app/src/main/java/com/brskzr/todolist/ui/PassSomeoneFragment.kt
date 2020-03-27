package com.brskzr.todolist.ui


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.brskzr.todolist.R
import com.brskzr.todolist.models.TodoItemDataModel
import com.brskzr.todolist.models.TodoItemType
import kotlinx.android.synthetic.main.fragment_pass_someone.*
import java.time.LocalDateTime

class PassSomeoneFragment : Fragment(), SaveTaskHostActivity.ISaveTaskEventHandler{

    private lateinit var dataHandler: IDataHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pass_someone, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataHandler = context as IDataHandler
    }

    private fun validate(): Boolean {

        if(et_someone.isEmpty()){
            toast("Who is for task, Please enter...")
            return false
        }

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

    override fun onSave() {
        if(!validate()) return

        val model = TodoItemDataModel(
            0,
            true,
            dtp_plandate.selectedDate,
            TodoItemType.PASS_SOMEONE,
            et_tagname.text.toString(),
            emptyList(),
            et_someone.text.toString()
        )

        dataHandler.onDataCreate(model)
    }
}
