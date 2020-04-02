package com.brskzr.todolist

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brskzr.todolist.adapters.*
import com.brskzr.todolist.models.Constants
import com.brskzr.todolist.models.TodoItemDataModel
import com.brskzr.todolist.ui.DialogButtonsFragment
import com.brskzr.todolist.ui.SaveTaskHostActivity
import com.brskzr.todolist.ui.toast
import com.brskzr.todolist.viewmodels.MainViewModel
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.floating_menu.*

class MainActivity : AppCompatActivity(), DialogButtonsFragment.IActionHandler {
    lateinit var rfabHelper: RapidFloatingActionHelper
    private lateinit var viewModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpMenu()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.listOfNote.observe(this, Observer {
            if(it.any()){
                rv_note_for_later.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                rv_note_for_later.adapter = NoteForLaterAdapter(it, { item ->
                    DialogButtonsFragment().withActions(false, false).open(this, item)
                })
                rv_note_for_later.setHasFixedSize(true)
            }
        })

        viewModel.listPassSomone.observe(this, Observer {
            if(it.any()){
                rv_pass_someone.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                rv_pass_someone.adapter = PassSomeoneAdapter(it)
                rv_pass_someone.setHasFixedSize(true)
            }
        })

        viewModel.listOfdoItImmediate.observe(this, Observer {
            if(it.any()){
                rv_do_it_immediate.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                rv_do_it_immediate.adapter = DoItImmediateAdapter(it)
                rv_do_it_immediate.setHasFixedSize(true)
            }
        })

        viewModel.listOfPlanIt.observe(this, Observer {
            if(it.any()){
                rv_plan_it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                rv_plan_it.adapter = PlanForLaterAdapter(it)
                rv_plan_it.setHasFixedSize(true)
            }
        })
    }

    override fun onComplete(todoItemDataModel: TodoItemDataModel) {
        toast(todoItemDataModel.Id.toString())
    }

    override fun onEdit(todoItemDataModel: TodoItemDataModel) {
        toast(todoItemDataModel.Id.toString())
    }

    override fun onRemove(todoItemDataModel: TodoItemDataModel) {
        viewModel.deleteItem(todoItemDataModel)
    }

    override fun onShare(todoItemDataModel: TodoItemDataModel) {
        toast(todoItemDataModel.Id.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            0 -> viewModel.getDoItImmediates()
            1 -> viewModel.getPlanIts()
            2 -> viewModel.getPassSomeones()
            3 -> viewModel.getNotes()
        }
    }

    private fun openSaveReminder(position:Int) {
        rfabHelper.toggleContent()
        val intent = Intent(this@MainActivity, SaveTaskHostActivity::class.java)
        intent.putExtra(Constants.TASK_TYPE_KEY, position)
        startActivityForResult(intent, position)
    }

    private fun setUpMenu(){
        val rfaContent = RapidFloatingActionContentLabelList(this)
        rfaContent.setOnRapidFloatingActionContentLabelListListener(object : RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener<Int> {
            override fun onRFACItemIconClick(position: Int, item: RFACLabelItem<Int>?) {
                openSaveReminder(position)
            }
            override fun onRFACItemLabelClick(position: Int, item: RFACLabelItem<Int>?) {
                openSaveReminder(position)
            }
        })

        var items = mutableListOf<RFACLabelItem<Int>>()
        items.add(RFACLabelItem<Int>()
            .setLabel(resources.getString(R.string.do_it_immediate))
            .setResId(R.drawable.ic_do_it_immediate)
            .setLabelSizeSp(16)
            .setLabelColor(Color.WHITE)
            .setLabelBackgroundDrawable(getDrawable( R.drawable.shape_bg_rec_green))
            .setWrapper(0)
        )

        items.add(RFACLabelItem<Int>()
            .setLabel(resources.getString(R.string.plan_for_later))
            .setResId(R.drawable.ic_plan_it)
            .setLabelSizeSp(16)
            .setLabelColor(Color.WHITE)
            .setLabelBackgroundDrawable(getDrawable( R.drawable.shape_bg_rec_blue))
            .setWrapper(1)
        )

        items.add(RFACLabelItem<Int>()
            .setLabel(resources.getString(R.string.pass_someone))
            .setResId(R.drawable.ic_delegate_it)
            .setLabelBackgroundDrawable(getDrawable( R.drawable.shape_bg_rec_yellow))
            .setLabelSizeSp(16)
            .setLabelColor(Color.WHITE)
            .setWrapper(2)
        )

        items.add(RFACLabelItem<Int>()
            .setLabel(resources.getString(R.string.note_for_later))
            .setResId(R.drawable.ic_dont_do_it)
            .setLabelColor(R.color.colorLabelRed)
            .setLabelSizeSp(16)
            .setLabelColor(Color.WHITE)
            .setLabelBackgroundDrawable(getDrawable( R.drawable.shape_bg_rec_red))
            .setWrapper(3)
        )

        rfaContent.setItems(items.toList())
        rfabHelper = RapidFloatingActionHelper(this,
            activity_main_rfal ,
            activity_main_rfab,
            rfaContent
        ).build()
    }
}
