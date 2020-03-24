package com.brskzr.todolist.ui


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.brskzr.todolist.R


class PlanForLaterFragment : Fragment() {

    private lateinit var saveTaskHandler: IDataHandler

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
}
