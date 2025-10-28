package com.example.appnav_view_comp.ui.theme

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appnav_view_comp.destinations
import com.example.appnav_view_comp.ui.theme.create.AddTaskScreen
import com.example.appnav_view_comp.ui.theme.detail.TaskDetailScreen
import com.example.appnav_view_comp.ui.theme.list.TaskListScreen
import com.example.appnav_view_comp.ui.theme.list.TaskListViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun TaskApp() {
    val viewModel: TaskListViewModel = hiltViewModel()
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Task App",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            )
        },
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = destinations.List) {
            val hostModifier = Modifier
                .consumeWindowInsets(innerPadding)
                .padding(innerPadding)
            composable<destinations.List> {
                TaskListScreen(
                    modifier = hostModifier,
                    onNavigateToDetail = { id ->
                        navController.navigate(destinations.Detail(id))
                    },
                    onNavigateToCreate = { navController.navigate(destinations.Create) },
                    navController = navController
                )
            }
            composable<destinations.Detail> {
                TaskDetailScreen(
                    modifier = hostModifier,
                    onCancel = { navController.popBackStack() },
                    navController = navController
                    )
            }
            composable<destinations.Create> {
                AddTaskScreen(
                    modifier = hostModifier,
                    onSave = { navController.popBackStack() },
                    onCancel = { navController.popBackStack() },
                    navController = navController
                )

            }
        }
    }
}