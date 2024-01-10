package com.todoapp.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.datastore.dataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.todoapp.R
import com.todoapp.databinding.FragmentAddTaskBinding
import com.todoapp.db.ToDoListSerializer
import com.todoapp.model.ToDo
import com.todoapp.notification.Notification
import com.todoapp.utils.Picker
import com.todoapp.utils.fullDate
import com.todoapp.utils.hour
import com.todoapp.utils.minute
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.launch

val Context.dataStore by dataStore("mainFile.json", ToDoListSerializer())

class AddTask : Fragment() {
    private lateinit var binding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dateTextInput.setOnClickListener {
            Picker(parentFragmentManager, binding.dateTextInput)
        }

        binding.addTaskBTN.setOnClickListener {

            val newToDo = ToDo(
                title = binding.addTitle.text.toString(),
                description = binding.addDescription.text.toString(),
                time = "$hour:$minute",
                date = fullDate,
                isDone = false
            )
//Toast.makeText(requireContext(),newToDo.toString(),Toast.LENGTH_LONG).show()
            lifecycleScope.launch {
                requireContext().dataStore.updateData {
                    it.copy(
                        it.toDoList.mutate {
                            it.add(newToDo)
                        }
                    )
                }
                Notification(
                    requireContext(),
                    newToDo.hashCode().toString(),
                    newToDo,
                    newToDo.title,
                    newToDo.description
                )

                Navigation.findNavController(binding.addTaskBTN)
                    .navigate(R.id.action_addTask_to_currentToDos)

            }

        }
    }
}