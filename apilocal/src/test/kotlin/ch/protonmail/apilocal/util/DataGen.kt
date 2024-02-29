package ch.protonmail.apilocal.util

import ch.protonmail.data.TaskDTO

fun mknew(): List<TaskDTO> = List(5) { it.toString() }
    .map {
        TaskDTO(
            creationDate = it,
            dueDate = it,
            encryptedDescription = it,
            encryptedTitle = it,
            id = it,
            image = it
        )
    }
