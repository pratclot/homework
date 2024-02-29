package ch.protonmail.android.protonmailtest.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.protonmail.common.DownloadTracker
import ch.protonmail.usecase.FetchSelectedTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val downloadTracker: DownloadTracker,
    private val fetchSelectedTask: FetchSelectedTask
) : ViewModel() {

    val selectedTask = fetchSelectedTask()
        .filterNotNull()

    fun imageDownloadedSuccessfully(taskId: String) = viewModelScope.launch {
        downloadTracker.markAsDownloaded(taskId)
    }
}
