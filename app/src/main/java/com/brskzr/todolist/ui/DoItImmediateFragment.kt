package com.brskzr.todolist.ui


import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.brskzr.todolist.R
import com.brskzr.todolist.components.OnPickHandler
import com.brskzr.todolist.components.TimePickerFragment
import com.brskzr.todolist.models.Constants
import com.brskzr.todolist.models.TodoItemDataModel
import com.brskzr.todolist.models.TodoItemType
import com.brskzr.todolist.viewmodels.SaveTaskHostViewModel
import kotlinx.android.synthetic.main.fragment_do_it_immediate.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class DoItImmediateFragment : Fragment(), SaveTaskHostActivity.ISaveTaskEventHandler, OnPickHandler{

    private var dateTime: LocalDateTime = LocalDateTime.now()
    private val timeFormatter : DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private lateinit var viewModel: SaveTaskHostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            viewModel = ViewModelProvider(it).get(SaveTaskHostViewModel::class.java)
        }

        viewModel.selectedItemId?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_do_it_immediate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        tv_reminde_at.setText(dateTime.format(timeFormatter))
        ll_remind_at.setOnClickListener { openTimeDialog() }
    }

    private fun openTimeDialog() {
        activity?.let {
            TimePickerFragment(this).open(it.supportFragmentManager ,dateTime)
        }
    }

    override fun onPick(sender: DialogFragment, localDateTime: LocalDateTime) {
        dateTime = localDateTime
        tv_reminde_at.setText(localDateTime.format(timeFormatter))
    }

    private fun validate() : Boolean {
        if(et_tagname.isEmpty()){
            toast("Lütfen tag giriniz!")
            return false
        }

        if(LocalDateTime.now() > dateTime){
            toast("İleri bir zaman giriniz.")
            return false
        }

        return true;
    }

    override fun onSave() {
        if(!validate())
            return

        val model = TodoItemDataModel(
            0,
            true,
            dateTime.toDate(),
            TodoItemType.DO_IT_IMMEDIATE,
            et_tagname.text.toString(),
            emptyList(),
            "",
            false
        )

        viewModel.addNewItem(model, {
            activity?.setResult(0, Intent())
            activity?.finish()
        })
    }
}

