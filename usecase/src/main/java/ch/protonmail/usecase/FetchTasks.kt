package ch.protonmail.usecase

import ch.protonmail.common.AppLogger
import ch.protonmail.common.DownloadTracker
import ch.protonmail.common.mapAsync
import ch.protonmail.repository.TasksRepository
import ch.protonmail.state.AppState
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchTasks @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val taskMapper: TaskMapper,
    private val downloadTracker: DownloadTracker,
    private val appState: AppState,
    private val appLogger: AppLogger
) {
    operator fun invoke() = flow {
        var ts: Long
        tasksRepository.fetch()
            .also { ts = System.currentTimeMillis() }
            .mapAsync { taskMapper.run { it.toUI() } }
            .also {
                ((System.currentTimeMillis() - ts) / 1_000f).let {
                    appLogger.d("Total decryption time is $it seconds")
                }
            }
            .let { emit(it) }
    }
        .combine(appState.isOffline) { tasksUI, isOffline ->
            tasksUI.map { it.copy(isAppOnline = !isOffline) }
        }
        .combine(downloadTracker.downloadedTasks) { tasksUI, downloadsMap ->
            tasksUI.map { it.copy(shouldDownloadImage = downloadsMap[it.id] == true) }
        }
}
