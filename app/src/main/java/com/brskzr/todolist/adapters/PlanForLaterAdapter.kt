package com.brskzr.todolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brskzr.todolist.R
import com.brskzr.todolist.models.TodoItemDataModel
import com.brskzr.todolist.ui.formatDateTime

class PlanForLaterAdapter(val items:List<TodoItemDataModel>) : RecyclerView.Adapter<PlanForLaterAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                            .from(parent.context)
                            .inflate(com.brskzr.todolist.R.layout.row_plan_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(item: TodoItemDataModel) {
            val tvNoteDate = view.findViewById(R.id.tv_reminde_at) as TextView
            val tvNoteDetail = view.findViewById(R.id.tv_task_detail) as TextView
            val tvCheckList = view.findViewById(R.id.tv_checklist) as TextView

            tvNoteDate.setText(item.remindAt.formatDateTime())
            tvNoteDetail.setText(item.tag)

            val checklist = if(item.checkList?.any())  item.checkList.map { "*${it.tag}"  }.joinToString() else ""
            tvCheckList.setText(checklist)
        }
    }
}