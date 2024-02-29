package ch.protonmail.android.protonmailtest.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.protonmail.state.AppState
import ch.protonmail.state.NavState
import ch.protonmail.uimodels.TaskUI
import ch.protonmail.usecase.FetchTasks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SUBSCRIBER_TIMEOUT = 5_000L

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appState: AppState,
    private val fetchTasks: FetchTasks,
    private val navState: NavState
) : ViewModel() {
    private val tasks: Flow<List<TaskUI>> = fetchTasks()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(SUBSCRIBER_TIMEOUT),
            emptyList()
        )

    val tasksUpcoming = tasks.map { it.sortedBy { it.dueDate } }
    val tasksUpcomingCount = tasksUpcoming.map { it.size }
    val tasksAll = tasks.map { it.sortedBy { it.creationDate } }
    val tasksAllCount = tasksAll.map { it.size }

    val showOfflineIndicator = appState.isOffline

    private val _taskClickedEvent = MutableSharedFlow<Unit>()
    val taskClickedEvent = _taskClickedEvent.asSharedFlow()

    fun taskClicked(taskUI: TaskUI) = viewModelScope.launch {
        navState.select(taskUI)
        _taskClickedEvent.emit(Unit)
    }
}
