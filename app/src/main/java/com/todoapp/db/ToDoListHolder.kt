package com.todoapp.db

import com.todoapp.model.ToDo
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data class ToDoListHolder(
    @Serializable(ToDoListListSerializer::class)
    val toDoList : PersistentList<ToDo> = persistentListOf()
)
