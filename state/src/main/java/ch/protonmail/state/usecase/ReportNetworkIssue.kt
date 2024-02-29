package ch.protonmail.state.usecase

import ch.protonmail.state.AppState
import javax.inject.Inject

class ReportNetworkIssue @Inject constructor(
    private val appState: AppState
) {
    operator fun invoke() {
        appState.offline()
    }
}
