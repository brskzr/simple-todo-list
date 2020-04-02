package com.brskzr.todolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brskzr.todolist.R
import com.brskzr.todolist.models.TodoItemDataModel
import com.brskzr.todolist.ui.formatDateTime
import java.lang.String.format
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class NoteForLaterAdapter(val items:List<TodoItemDataModel>, val onClick: (TodoItemDataModel) -> Unit) : RecyclerView.Adapter<NoteForLaterAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                            .from(parent.context)
                            .inflate(com.brskzr.todolist.R.layout.row_note_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position], onClick)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(item: TodoItemDataModel, onClick: (TodoItemDataModel) -> Unit) {
            val tvNoteDate = view.findViewById(R.id.tv_note_date) as TextView
            val tvNoteDetail = view.findViewById(R.id.tv_note_detail) as TextView

            tvNoteDate.setText(item.remindAt.formatDateTime())
            tvNoteDetail.setText(item.tag)


            view.setOnClickListener {
                onClick(item)
            }
        }
    }
}