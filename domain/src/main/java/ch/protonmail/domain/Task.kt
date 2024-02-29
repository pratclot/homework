package ch.protonmail.domain

data class Task(
    val creationDate: String,
    val dueDate: String,
    val encryptedDescription: String,
    val encryptedTitle: String,
    val id: String,
    val image: String
)
