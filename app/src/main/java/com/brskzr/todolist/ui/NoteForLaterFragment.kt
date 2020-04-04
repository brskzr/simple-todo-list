package com.brskzr.todolist.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.brskzr.todolist.R
import com.brskzr.todolist.models.TodoItemDataModel
import com.brskzr.todolist.models.TodoItemType
import com.brskzr.todolist.viewmodels.SaveTaskHostViewModel
import kotlinx.android.synthetic.main.fragment_do_it_immediate.*
import kotlinx.android.synthetic.main.fragment_note_for_later.*
import kotlinx.android.synthetic.main.fragment_note_for_later.et_tagname
import java.util.*


class NoteForLaterFragment : Fragment(), SaveTaskHostActivity.ISaveTaskEventHandler{
    private lateinit var viewModel: SaveTaskHostViewModel
    private var model:TodoItemDataModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_for_later, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            viewModel = ViewModelProvider(it).get(SaveTaskHostViewModel::class.java)
        }

        if(viewModel.isUpdate){
            viewModel.getModel(viewModel.selectedItemId).observe(this, Observer {
                model = it
                it?.let {
                    et_tagname.setText(it.tag)
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onSave() {
        if(viewModel.isUpdate) {
            model?.apply {
                remindAt = Date()
                tag = et_tagname.text.toString()
            }?.also {
                viewModel.updateItem(it, {
                    activity?.setResult(0, Intent())
                    activity?.finish()
                })
            }
        }
        else {
            val model = TodoItemDataModel(0,
                false,
                Date(),
                TodoItemType.NOTE_FOR_LATER,
                et_tagname.text.toString(),
                emptyList(),
                "",
                false)

            viewModel.addNewItem(model, {
                activity?.setResult(0, Intent())
                activity?.finish()
            })
        }
    }
}
