package com.example.appnav_view_comp

import kotlinx.serialization.Serializable

@Serializable
sealed class destinations (val route: String) {
    @Serializable
    object List: destinations("TaskListScreen")
    @Serializable
    object Create: destinations("AddTaskScreen")
    @Serializable
    data class Detail (val id:Long): destinations("TaskDetailScreen/$id")
}