package ch.protonmail.repository

import ch.protonmail.apilocal.CacheDB
import ch.protonmail.apiremote.TaskApi
import ch.protonmail.common.IO_DISPATCHER
import ch.protonmail.domain.Task
import ch.protonmail.state.usecase.ClearNetworkIssue
import ch.protonmail.state.usecase.ReportNetworkIssue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Named

class TasksRepositoryImpl @Inject constructor(
    private val taskApi: TaskApi,
    @Named(IO_DISPATCHER)
    private val dispatcher: CoroutineDispatcher,
    private val cacheDB: CacheDB,
    private val reportNetworkIssue: ReportNetworkIssue,
    private val clearNetworkIssue: ClearNetworkIssue
) : TasksRepository {
    override suspend fun fetch(): List<Task> = withContext(dispatcher) {
        kotlin.runCatching {
            taskApi.fetch()
                .also {
                    clearNetworkIssue()
                    cacheDB.write(it)
                }
        }
            .recover {
                when (it) {
                    is UnknownHostException,
                    is SocketTimeoutException -> {
                        reportNetworkIssue()
                        cacheDB.fetch()
                    }

                    else -> throw it
                }
            }
            .getOrThrow()
            .map { it.toDomain() }
    }
}
