package com.todoapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.todoapp.R
import com.todoapp.adaptor.ToDoAdaptor
import com.todoapp.databinding.FragmentAddTaskBinding
import com.todoapp.databinding.FragmentCurrentToDosBinding
import com.todoapp.model.ToDo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

lateinit var currentToDosBinding: FragmentCurrentToDosBinding
class CurrentToDos : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        currentToDosBinding = FragmentCurrentToDosBinding.inflate(inflater, container, false)
        return currentToDosBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentToDosBinding.addTaskButton.setOnClickListener {
            Navigation.findNavController(currentToDosBinding.addTaskButton)
                .navigate(R.id.action_currentToDos_to_addTask)
        }
        lifecycleScope.launch {
            initRecView()
        }

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            initRecView()
        }
    }

    private suspend fun initRecView() {
        val adapter = ToDoAdaptor(
            requireContext().dataStore.data.first().toDoList.toMutableList(),
            requireContext()
        )
        currentToDosBinding.recView.adapter = adapter
        currentToDosBinding.recView.layoutManager = LinearLayoutManager(requireContext())


    }
}