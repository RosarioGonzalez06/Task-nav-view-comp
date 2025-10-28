package com.example.appnav_view_comp.ui.theme.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appnav_view_comp.data.model.Task
import com.example.appnav_view_comp.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    fun addTask(title: String) {
        viewModelScope.launch {
            val newTask = Task(
                id = System.currentTimeMillis(),
                title = title,
                isDone = false
            )
            repository.addTask(newTask)
        }
    }
}
