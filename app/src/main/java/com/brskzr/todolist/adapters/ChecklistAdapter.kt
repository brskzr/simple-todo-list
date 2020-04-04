package com.brskzr.todolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brskzr.todolist.models.ChecklistItem
import com.brskzr.todolist.R


class ChecklistAdapter(val items: MutableList<ChecklistItem>, onClick: CheckListAdapterListener) : RecyclerView.Adapter<ChecklistAdapter.ModelViewHolder>() {

    private var onClick = onClick

    fun addRange(data: List<ChecklistItem>){
        items.addAll(data)
    }
    fun addItem(item: ChecklistItem){
        items.add(0, item)
        notifyItemInserted(0)
    }

    fun removeItem(index:Int){
        items.removeAt(index)
        notifyItemRemoved(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.brskzr.todolist.R.layout.row_checlist_item, parent, false)

        return ModelViewHolder(view, onClick)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.bindItems(items.get(position))
    }

    class ModelViewHolder(val view: View, val onClick: CheckListAdapterListener) : RecyclerView.ViewHolder(view){

        fun bindItems(item: ChecklistItem) {
            val cbIsChecked = view.findViewById(R.id.cbIsChecked) as CheckBox
            val tvItemTagName = view.findViewById(R.id.tvItemTagName) as TextView
            val btnDelete = view.findViewById<ImageView>(R.id.imgDeleteItem)

            btnDelete.setOnClickListener {
                onClick.deleteOnClick(it, getAdapterPosition())
            }

            cbIsChecked.isChecked = item.isChecked
            tvItemTagName.setText(item.tag)

            cbIsChecked.setOnCheckedChangeListener { buttonView, isChecked -> item.isChecked = isChecked  }
        }
    }
}

interface CheckListAdapterListener {
    fun deleteOnClick(v: View, position: Int)
}