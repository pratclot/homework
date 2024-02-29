package ch.protonmail.usecase.util

import ch.protonmail.domain.Task

fun mknew(
    creationDate: String = "",
    dueDate: String = "",
    encryptedDescription: String = "",
    encryptedTitle: String = "",
    id: String = "",
    image: String = ""
) = Task(
    creationDate = creationDate,
    dueDate = dueDate,
    encryptedDescription = encryptedDescription,
    encryptedTitle = encryptedTitle,
    id = id,
    image = image
)
