package ch.protonmail.uimodels

data class TaskUI(
    val creationDate: String,
    val dueDate: String,
    val description: String,
    val title: String,
    val id: String,
    val image: String,
    val shouldDownloadImage: Boolean,
    val isAppOnline: Boolean
)
