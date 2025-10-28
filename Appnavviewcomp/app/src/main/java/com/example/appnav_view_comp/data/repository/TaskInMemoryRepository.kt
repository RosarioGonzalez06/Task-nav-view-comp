package com.example.appnav_view_comp.data.repository
import com.example.appnav_view_comp.data.model.Task
import kotlinx.coroutines.delay
import javax.inject.Inject

class TaskInMemoryRepository @Inject constructor(): TaskRepository {
    val taskList: MutableList<Task> = mutableListOf(
        Task(
            id = 1,
            title = "Hacer la compra",
            isDone = false,
        ),
        Task(
            id = 2,
            title = "Cocinar",
            isDone = true,
        ),
        Task(
            id = 3,
            title = "Hacer deberes",
            isDone = false,
        ),
        Task(
            id = 4,
            title = "Pasear al perro",
            isDone = true,
        )
    )

    override suspend fun toggleTaskDone(id: Long) {
        taskList.replaceAll { task ->
            if (task.id == id) task.copy(isDone = !task.isDone) else task
        }
    }

    override suspend fun readOne(id: Long): Task? {
        val task = taskList.firstOrNull() {
                p -> p.id == id
        }
        return task
    }

    override suspend fun readAll(): List<Task> {
        delay(500L)
        return this.taskList
    }

    override suspend fun addTask(task: Task) {
        taskList.add(task)
    }

    override suspend fun deleteTask(id: Long) {
        taskList.removeIf{it.id==id}
    }
}