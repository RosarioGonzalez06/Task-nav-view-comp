package com.example.appnav_view_comp.ui.theme.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun TaskListItem(
    title: String,
    isDone: Boolean,
    taskId: Long,
    modifier: Modifier = Modifier,
    onNavigateToDetail: (Long) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = { onNavigateToDetail(taskId)
                })
    ) {
        Row(
            modifier = modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(text = if (isDone) " - Completada" else " - Pendiente")
        }
    }
}

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel = hiltViewModel(),
    onNavigateToDetail: (Long) -> Unit,
    onNavigateToCreate: () -> Unit,
    modifier: Modifier,
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentBackStackEntry = navController.currentBackStackEntry
    val savedStateHandle = currentBackStackEntry?.savedStateHandle

    LaunchedEffect(savedStateHandle) {
        savedStateHandle?.getLiveData<Boolean>("refresh")?.observeForever { refresh ->
            if (refresh == true) {
                viewModel.loadTask()
                savedStateHandle["refresh"] = false
            }
        }
    }
    when (uiState) {
        is ListUiState.Initial -> {}
        is ListUiState.Loading -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        is ListUiState.Success -> {
            val tasks = (uiState as ListUiState.Success).tasks

            Column(
                modifier = modifier.fillMaxSize()
            ) {
                Button(
                    onClick = onNavigateToCreate,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Crear tarea")
                }
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    items(tasks, key = { it.id }) { task ->
                        TaskListItem(
                            title = task.title,
                            isDone = task.isDone,
                            taskId = task.id,
                            onNavigateToDetail = onNavigateToDetail
                        )
                    }

                }

            }
        }
        is ListUiState.Error -> {
            Text("Error cargando tareas")
        }
    }
}


