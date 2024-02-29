package ch.protonmail.repository

import ch.protonmail.data.TaskDTO
import ch.protonmail.domain.Task

fun TaskDTO.toDomain() = Task(
    creationDate = creationDate ?: "",
    dueDate = dueDate ?: "",
    encryptedDescription = encryptedDescription ?: "",
    encryptedTitle = encryptedTitle ?: "",
    id = id ?: "",
    image = image ?: ""
)
