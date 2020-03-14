package com.brskzr.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList
import kotlinx.android.synthetic.main.floating_menu.*

class MainActivity : AppCompatActivity() {

    lateinit var rfabHelper: RapidFloatingActionHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rfaContent = RapidFloatingActionContentLabelList(this)
        rfaContent.setOnRapidFloatingActionContentLabelListListener(object : RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener<Int> {
            override fun onRFACItemIconClick(position: Int, item: RFACLabelItem<Int>?) {
                Toast.makeText(this@MainActivity, "clicked label: " + position, Toast.LENGTH_SHORT).show();
                rfabHelper.toggleContent();

            }

            override fun onRFACItemLabelClick(position: Int, item: RFACLabelItem<Int>?) {
                Toast.makeText(this@MainActivity, "clicked label: " + position, Toast.LENGTH_SHORT).show();
                rfabHelper.toggleContent();
            }
        })

        var items = mutableListOf<RFACLabelItem<Int>>()
        items.add(RFACLabelItem<Int>()
                        .setLabel("Github: wangjiegulu")
                        .setResId(R.drawable.ic_menu_planning)
                        .setWrapper(0)
        )

        items.add(RFACLabelItem<Int>()
            .setLabel("Github: wangjiegulu")
            .setResId(R.drawable.ic_menu_planning)
            .setWrapper(0)
        )

        items.add(RFACLabelItem<Int>()
            .setLabel("Github: wangjiegulu")
            .setResId(R.drawable.ic_menu_planning)
            .setWrapper(0)
        )

        items.add(RFACLabelItem<Int>()
            .setLabel("Github: wangjiegulu")
            .setResId(R.drawable.ic_menu_planning)
            .setWrapper(0)
        )

        rfaContent.setItems(items.toList())
        rfabHelper = RapidFloatingActionHelper(this,
            activity_main_rfal ,
            activity_main_rfab,
                rfaContent
        ).build()
    }
}
