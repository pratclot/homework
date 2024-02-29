package ch.protonmail.state

import ch.protonmail.uimodels.TaskUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavState @Inject constructor() {

    private val _selectedTaskUI = MutableStateFlow<TaskUI?>(null)
    val selectedTaskUI = _selectedTaskUI.asStateFlow()

    fun select(newTaskUI: TaskUI) {
        _selectedTaskUI.value = newTaskUI
    }
}
