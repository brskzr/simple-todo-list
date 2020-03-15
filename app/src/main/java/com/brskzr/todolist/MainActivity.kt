package com.brskzr.todolist

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brskzr.todolist.ui.SaveReminderActivity
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


    private fun setUpMenu(){
        val rfaContent = RapidFloatingActionContentLabelList(this)
        rfaContent.setOnRapidFloatingActionContentLabelListListener(object : RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener<Int> {
            override fun onRFACItemIconClick(position: Int, item: RFACLabelItem<Int>?) {
                Toast.makeText(this@MainActivity, "clicked label: " + position, Toast.LENGTH_SHORT).show();
                rfabHelper.toggleContent();

                val intent = Intent(this@MainActivity, SaveReminderActivity::class.java)
                startActivity(intent)
            }

            override fun onRFACItemLabelClick(position: Int, item: RFACLabelItem<Int>?) {
                Toast.makeText(this@MainActivity, "clicked label: " + position, Toast.LENGTH_SHORT).show();
                rfabHelper.toggleContent();

                val intent = Intent(this@MainActivity, SaveReminderActivity::class.java)
                intent.putExtra("toolbar_color", item?.labelColor)
                startActivity(intent)
            }
        })

        var items = mutableListOf<RFACLabelItem<Int>>()
        items.add(RFACLabelItem<Int>()
            .setLabel("Do It")
            .setResId(R.drawable.ic_do_it_immediate)
            .setLabelSizeSp(16)
            .setLabelColor(Color.WHITE)
            .setLabelBackgroundDrawable(getDrawable( R.drawable.shape_bg_rec_green))
            .setWrapper(0)
        )

        items.add(RFACLabelItem<Int>()
            .setLabel("Plan It")
            .setResId(R.drawable.ic_plan_it)
            .setLabelSizeSp(16)
            .setLabelColor(Color.WHITE)
            .setLabelBackgroundDrawable(getDrawable( R.drawable.shape_bg_rec_blue))
            .setWrapper(1)
        )

        items.add(RFACLabelItem<Int>()
            .setLabel("Delegate It")
            .setResId(R.drawable.ic_delegate_it)
            .setLabelBackgroundDrawable(getDrawable( R.drawable.shape_bg_rec_yellow))
            .setLabelSizeSp(16)
            .setLabelColor(Color.WHITE)
            .setWrapper(2)
        )

        items.add(RFACLabelItem<Int>()
            .setLabel("Dont Care")
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
