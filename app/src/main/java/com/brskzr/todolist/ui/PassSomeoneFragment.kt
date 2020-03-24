package com.brskzr.todolist.ui


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.brskzr.todolist.R

/**
 * A simple [Fragment] subclass.
 */
class PassSomeoneFragment : Fragment() {

    private lateinit var saveTaskHandler: IDataHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pass_someone, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        saveTaskHandler = context as IDataHandler
    }
}
