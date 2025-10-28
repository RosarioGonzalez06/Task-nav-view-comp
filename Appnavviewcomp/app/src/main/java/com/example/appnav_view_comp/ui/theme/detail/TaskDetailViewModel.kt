package com.example.appnav_view_comp.ui.theme.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.appnav_view_comp.data.model.Task
import com.example.appnav_view_comp.data.repository.TaskRepository
import com.example.appnav_view_comp.destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository,

): ViewModel() {
    private val _uiState= MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState>
        =_uiState.asStateFlow()
    private var taskId: Long = 0
    init {
        viewModelScope.launch {
            val route= savedStateHandle.toRoute<destinations.Detail>()
            taskId=route.id
            loadTask()
            }

        }
    private fun loadTask() {
        viewModelScope.launch {
            val task = taskRepository.readOne(taskId)
            task?.let {
                _uiState.value = it.toDetailUiState()
            }
        }
    }
    fun toggleTaskDone() {
        viewModelScope.launch {
            taskRepository.toggleTaskDone(taskId)
            loadTask()
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            taskRepository.deleteTask(taskId)
        }
    }
    fun Task.toDetailUiState(): DetailUiState = DetailUiState(
        title = this.title,
        isDone = this.isDone,
    )
}
data class DetailUiState(
    val title:String="",
    val isDone:Boolean= false,
)