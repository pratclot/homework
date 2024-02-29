package ch.protonmail.common

import kotlinx.coroutines.flow.Flow

interface DownloadTracker {
    val downloadedTasks: Flow<Map<String, Boolean>>
    suspend fun markAsDownloaded(id: String)
}
