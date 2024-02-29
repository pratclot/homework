package ch.protonmail.apilocal

import ch.protonmail.data.TaskDTO

interface CacheDB {
    suspend fun fetch(): List<TaskDTO>
    suspend fun write(tasks: List<TaskDTO>)
}
