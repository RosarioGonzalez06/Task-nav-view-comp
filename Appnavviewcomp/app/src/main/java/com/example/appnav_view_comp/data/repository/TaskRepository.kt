package com.example.appnav_view_comp.data.repository

import com.example.appnav_view_comp.data.model.Task

interface TaskRepository {
    suspend fun readOne(id:Long): Task?
    suspend fun readAll():List<Task>
    suspend fun addTask(task: Task)
    suspend fun deleteTask(id: Long)
    suspend fun toggleTaskDone(id: Long)
}