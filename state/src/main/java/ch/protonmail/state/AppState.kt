package ch.protonmail.state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppState @Inject constructor() {
    private val _isOffline = MutableStateFlow(false)
    val isOffline = _isOffline.asStateFlow()

    fun offline() = toggleState(true)

    fun online() = toggleState(false)

    private fun toggleState(isOfflineNewValue: Boolean) {
        _isOffline.value = isOfflineNewValue
    }
}
