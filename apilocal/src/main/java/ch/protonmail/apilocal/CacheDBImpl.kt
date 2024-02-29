package ch.protonmail.apilocal

import ch.protonmail.data.TaskDTO
import kotlinx.serialization.json.Json
import java.io.File

class CacheDBImpl(private val file: File) : CacheDB {
    override suspend fun fetch(): List<TaskDTO> = file.bufferedReader().use {
        it.readLines().map {
            Json.decodeFromString(TaskDTO.serializer(), it)
        }
    }

    override suspend fun write(tasks: List<TaskDTO>) {
        file.bufferedWriter().run {
            tasks.forEach {
                write(Json.encodeToString(TaskDTO.serializer(), it))
                write("\n")
            }
            close()
        }
    }
}
