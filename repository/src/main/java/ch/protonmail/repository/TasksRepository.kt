package ch.protonmail.repository

import ch.protonmail.domain.Task

interface TasksRepository {
    suspend fun fetch(): List<Task>
}
