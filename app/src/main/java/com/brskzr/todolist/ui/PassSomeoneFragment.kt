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

import com.brskzr.todolist.R
import com.brskzr.todolist.models.TodoItemDataModel
import com.brskzr.todolist.models.TodoItemType
import com.brskzr.todolist.viewmodels.SaveTaskHostViewModel
import kotlinx.android.synthetic.main.fragment_do_it_immediate.*
import kotlinx.android.synthetic.main.fragment_pass_someone.*
import kotlinx.android.synthetic.main.fragment_pass_someone.dtp_plandate
import kotlinx.android.synthetic.main.fragment_pass_someone.et_tagname
import kotlinx.android.synthetic.main.fragment_plan_for_later.*
import java.time.LocalDateTime

class PassSomeoneFragment : Fragment(), SaveTaskHostActivity.ISaveTaskEventHandler{

    private lateinit var viewModel: SaveTaskHostViewModel
    private var model:TodoItemDataModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            viewModel = ViewModelProvider(it).get(SaveTaskHostViewModel::class.java)
        }

        if(viewModel.isUpdate){
            viewModel.getModel(viewModel.selectedItemId).observe(this, Observer {
                it?.let {
                    model = it
                    dtp_plandate.initialDate(it.remindAt.toLocalDateTime())
                    et_tagname.setText(it.tag)
                    et_someone.setText(it.someone)
                }
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pass_someone, container, false)
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

        if(viewModel.isUpdate){
            model?.apply {
                remindAt = dtp_plandate.selectedDate
                tag = et_tagname.text.toString()
                someone = et_someone.text.toString()
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
                TodoItemType.PASS_SOMEONE,
                et_tagname.text.toString(),
                emptyList(),
                et_someone.text.toString(),
                false
            )

            viewModel.addNewItem(model, {
                activity?.setResult(0, Intent())
                activity?.finish()
            })
        }
    }
}
