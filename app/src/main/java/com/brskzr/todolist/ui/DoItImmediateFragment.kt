package com.brskzr.todolist.ui


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.brskzr.todolist.R
import com.brskzr.todolist.models.TodoItemDataModel


class DoItImmediateFragment : Fragment(), SaveTaskHostActivity.ISaveTaskEventHandler{

    private lateinit var dataHandler: IDataHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_do_it_immediate, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataHandler = context as IDataHandler
    }

    override fun onSave() {
        //val model = TodoItemDataModel()
        //dataHandler.onDataCreate(model)
    }
}
