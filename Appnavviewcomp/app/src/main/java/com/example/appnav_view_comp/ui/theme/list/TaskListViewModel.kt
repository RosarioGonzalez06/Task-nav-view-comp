    package com.example.appnav_view_comp.ui.theme.list

    import androidx.lifecycle.SavedStateHandle
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.appnav_view_comp.data.model.Task
    import com.example.appnav_view_comp.data.repository.TaskRepository
    import dagger.hilt.android.lifecycle.HiltViewModel
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.launch
    import javax.inject.Inject

    @HiltViewModel
    class TaskListViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle, private val repository: TaskRepository): ViewModel(){
        private val _uiState: MutableStateFlow<ListUiState> = MutableStateFlow(ListUiState.Initial)
        val uiState: StateFlow<ListUiState>
            get()= _uiState.asStateFlow()
        init {
                loadTask()
        }
        fun loadTask() {
            viewModelScope.launch {
                _uiState.value = ListUiState.Loading
                val allTasks = repository.readAll()
                _uiState.value = ListUiState.Success(allTasks.asListUiState())
            }
        }
    }

    fun Task.asListItemUiState(): ListItemUiState {
        return ListItemUiState(
            id = this.id,
            title = this.title,
            isDone = this.isDone
        )
    }

    fun List<Task>.asListUiState(): List<ListItemUiState> = this.map(Task::asListItemUiState)


    data class ListItemUiState(
        val id: Long,
        val title: String,
        val isDone: Boolean
    )
    sealed class ListUiState {
        object Initial : ListUiState()
        object Loading : ListUiState()
        data class Success(val tasks: List<ListItemUiState>) : ListUiState()
        object Error : ListUiState()
    }