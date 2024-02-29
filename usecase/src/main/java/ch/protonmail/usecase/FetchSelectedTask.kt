package ch.protonmail.usecase

import ch.protonmail.common.DownloadTracker
import ch.protonmail.state.NavState
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class FetchSelectedTask @Inject constructor(
    private val downloadTracker: DownloadTracker,
    private val navState: NavState
) {
    operator fun invoke() = navState.selectedTaskUI
        .combine(downloadTracker.downloadedTasks) { taskUI, downloadsMap ->
            taskUI?.let { it.copy(shouldDownloadImage = downloadsMap[it.id] == true) }
        }
}
