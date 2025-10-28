package com.example.appnav_view_comp.ui.theme.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun TaskDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskDetailViewModel = hiltViewModel(),
    onCancel: () -> Unit,
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = uiState.title,
            style = MaterialTheme.typography.headlineMedium
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Estado: ")
            Checkbox(
                checked = uiState.isDone,
                onCheckedChange = {
                    viewModel.toggleTaskDone()
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("refresh", true)
                }
            )
            Text(if (uiState.isDone) "Completada" else "Pendiente")
        }

        Button(onClick = onCancel) {
            Text("Volver")
        }

        Button(onClick = {
            viewModel.deleteTask()
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("refresh", true)
            navController.popBackStack()
        }) {
            Text("Eliminar tarea")
        }
    }
}
