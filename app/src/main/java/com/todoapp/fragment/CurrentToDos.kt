package com.todoapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.todoapp.R
import com.todoapp.adaptor.ToDoAdaptor
import com.todoapp.databinding.FragmentAddTaskBinding
import com.todoapp.databinding.FragmentCurrentToDosBinding
import com.todoapp.model.ToDo

val toDoList = mutableListOf<ToDo>()
class CurrentToDos : Fragment() {
    private lateinit var binding: FragmentCurrentToDosBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentToDosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding.addTaskButton.setOnClickListener{
           Navigation.findNavController(binding.addTaskButton).navigate(R.id.action_currentToDos_to_addTask)
       }
        initRecView()
    }

    override fun onResume() {
        super.onResume()
        initRecView()
    }

    private fun initRecView(){
        val adapter = ToDoAdaptor(toDoList,requireContext())
        binding.recView.adapter=adapter
        binding.recView.layoutManager = LinearLayoutManager(requireContext())


    }
}