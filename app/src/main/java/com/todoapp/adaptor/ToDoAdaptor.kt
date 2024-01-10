package com.todoapp.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.todoapp.R
import com.todoapp.fragment.currentToDosBinding
import com.todoapp.fragment.dataStore
import com.todoapp.model.ToDo
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.runBlocking

class ToDoAdaptor(private var todoList: MutableList<ToDo>, var context: Context) :
    RecyclerView.Adapter<ToDoAdaptor.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var titleTB: TextView = itemView.findViewById(R.id.titleTextBox)
        var descTB: TextView = itemView.findViewById(R.id.descTextBox)
        var dateTB: TextView = itemView.findViewById(R.id.dateTexBox)
        var timeTB: TextView = itemView.findViewById(R.id.timeTextBox)
        var isDoneCheckBox: CheckBox = itemView.findViewById(R.id.isDoneCB)

        init {
            isDoneCheckBox.setOnCheckedChangeListener { button, isSelected ->
                if (isSelected) {
                    todoList.removeAt(adapterPosition)
                    runBlocking {
                        context.dataStore.updateData {
                            it.copy(
                                it.toDoList.mutate {
                                    it.removeAt(adapterPosition)

                                }
                            )
                        }
                    }
                    //currentToDosBinding.recView.adapter!!.notifyDataSetChanged()
                    currentToDosBinding.recView.adapter!!.notifyItemRemoved(adapterPosition)
                    // Toast.makeText(context, adapterPosition.toString(), Toast.LENGTH_LONG).show()
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
        return todoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            titleTB.text = todoList[position].title
            descTB.text = todoList[position].description
            dateTB.text = todoList[position].date
            timeTB.text = todoList[position].time
            isDoneCheckBox.isChecked = todoList[position].isDone
            // Toast.makeText(context, position.toString(), Toast.LENGTH_LONG).show()

        }
    }

}