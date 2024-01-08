package com.todoapp.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.todoapp.R
import com.todoapp.fragment.currentToDosBinding
import com.todoapp.fragment.dataStore
import com.todoapp.model.ToDo
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.runBlocking

class ToDoAdaptor(private var toDoList: MutableList<ToDo>, var context: Context) :
    RecyclerView.Adapter<ToDoAdaptor.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card = itemView.findViewById<CardView>(R.id.card)
        var titleTB = itemView.findViewById<TextView>(R.id.titleTextBox)
        var descTB = itemView.findViewById<TextView>(R.id.descTextBox)
        var dateText = itemView.findViewById<TextView>(R.id.dateText)
        var timeText = itemView.findViewById<TextView>(R.id.timeText)
        var dateTB = itemView.findViewById<TextView>(R.id.dateTexBox)
        var timeTB = itemView.findViewById<TextView>(R.id.timeTextBox)
        var isDoneCheckBox: CheckBox = itemView.findViewById(R.id.isDoneCB)
        init {
            isDoneCheckBox.setOnCheckedChangeListener { button , isSelected ->
                if (isSelected) {
                   // Log.d("chi", "ahan ")
                    runBlocking {
                        context.dataStore.updateData {
                            it.copy(
                                it.toDoList.mutate {
                                    it.removeAt(adapterPosition)
                                }
                            )
                        }
                    }
                    currentToDosBinding.recView.adapter!!.notifyItemChanged(adapterPosition)
                   // Log.d("chi", "chi: ")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_in_recview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            titleTB.text = toDoList[position].title
            descTB.text = toDoList[position].description
            dateTB.text = toDoList[position].date
            timeTB.text = toDoList[position].time
            isDoneCheckBox.isChecked = toDoList[position].isDone
        }

    }
}