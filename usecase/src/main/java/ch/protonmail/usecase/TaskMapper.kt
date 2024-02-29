package ch.protonmail.usecase

import ch.protonmail.cryptowrapper.CryptoWrapper
import ch.protonmail.domain.Task
import ch.protonmail.uimodels.TaskUI
import ch.protonmail.usecase.di.DATE_TIME_FORMATTER
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject
import javax.inject.Named

internal const val E_INVALID_DATE = "E_INVALID_DATE"

class TaskMapper @Inject constructor(
    private val cryptoWrapper: CryptoWrapper,
    @Named(DATE_TIME_FORMATTER)
    private val dateTimeFormatter: DateTimeFormatter
) {

    suspend fun Task.toUI(shouldDownloadImage: Boolean = false, isAppOnline: Boolean = true) =
        cryptoWrapper.run {
            TaskUI(
                creationDate = creationDate.reformat(),
                dueDate = dueDate.reformat(),
                description = encryptedDescription.decrypt(),
                title = encryptedTitle.decrypt(),
                id = id,
                image = image,
                shouldDownloadImage = shouldDownloadImage,
                isAppOnline = isAppOnline
            )
        }

    private fun String.reformat() = kotlin.runCatching {
        dateTimeFormatter.run {
            DateTimeFormatter.ISO_DATE_TIME.parse(this@reformat).let {
                format(it)
            }
        }
    }
        .recover {
            when (it) {
                is DateTimeParseException -> E_INVALID_DATE
                else -> throw it
            }
        }
        .getOrThrow()
}
