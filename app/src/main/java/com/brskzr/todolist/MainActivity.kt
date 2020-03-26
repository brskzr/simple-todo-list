package com.brskzr.todolist

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brskzr.todolist.models.Constants
import com.brskzr.todolist.ui.SaveReminderActivity
import com.brskzr.todolist.ui.SaveTaskHostActivity
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList
import kotlinx.android.synthetic.main.floating_menu.*

class MainActivity : AppCompatActivity() {

    lateinit var rfabHelper: RapidFloatingActionHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpMenu()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            100 -> {
                //Todo refresh
            }
        }
    }

    private fun openSaveReminder(position:Int) {
        rfabHelper.toggleContent();
        val intent = Intent(this@MainActivity, SaveTaskHostActivity::class.java)
        intent.putExtra(Constants.TASK_TYPE_KEY, position)
        startActivityForResult(intent, 100)
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
