package com.brskzr.todolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brskzr.todolist.R
import com.brskzr.todolist.models.TodoItemDataModel
import com.brskzr.todolist.ui.formatDateTime
import com.brskzr.todolist.ui.toDate
import java.time.LocalDateTime

class DoItImmediateAdapter(val items:List<TodoItemDataModel>, val onClick: (TodoItemDataModel) -> Unit) : RecyclerView.Adapter<DoItImmediateAdapter.ViewHolder>() {

    val now:LocalDateTime
        get() = LocalDateTime.now()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                            .from(parent.context)
                            .inflate(com.brskzr.todolist.R.layout.row_doit_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position], now, onClick)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(item: TodoItemDataModel, now:LocalDateTime, onClick: (TodoItemDataModel) -> Unit) {
            val tvRemaining = view.findViewById(R.id.tv_remaining) as TextView
            val tvTaskDetail = view.findViewById(R.id.tv_task_detail) as TextView

            val diff = item.remindAt.time - now.toDate().time
            val remaining = if(diff > 0) "Remaining: ${diff / (1000 * 60)} minutes" else "It was at: ${item.remindAt.formatDateTime()}"

            tvRemaining.setText(remaining)
            tvTaskDetail.setText(item.tag)

            view.setOnClickListener {
                onClick(item)
            }
        }
    }
}