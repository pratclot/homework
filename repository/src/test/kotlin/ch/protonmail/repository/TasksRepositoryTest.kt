package ch.protonmail.repository

import ch.protonmail.apilocal.CacheDB
import ch.protonmail.apiremote.TaskApi
import ch.protonmail.commontest.CoroutineTestRule
import ch.protonmail.repository.util.mknew
import ch.protonmail.state.usecase.ClearNetworkIssue
import ch.protonmail.state.usecase.ReportNetworkIssue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException

class TasksRepositoryTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule(StandardTestDispatcher())

    private val taskApi: TaskApi = mockk()
    private val cacheDB: CacheDB = mockk()
    private val reportNetworkIssue: ReportNetworkIssue = mockk()
    private val clearNetworkIssue: ClearNetworkIssue = mockk()
    private val tasksRepository = TasksRepositoryImpl(
        taskApi = taskApi,
        dispatcher = coroutinesTestRule.testDispatcher,
        cacheDB = cacheDB,
        reportNetworkIssue = reportNetworkIssue,
        clearNetworkIssue = clearNetworkIssue
    )

    @Test
    fun `handles Internet problems gracefully`() = runTest {
        coEvery { taskApi.fetch() } throws UnknownHostException()

        tasksRepository.fetch()

        coVerify { reportNetworkIssue() }
        coVerify { cacheDB.fetch() }

        coVerify(exactly = 0) { clearNetworkIssue() }
        coVerify(exactly = 0) { cacheDB.write(any()) }
    }

    @Test
    fun `handles normal response properly`() = runTest {
        coEvery { taskApi.fetch() } returns mknew()

        tasksRepository.fetch()

        coVerify(exactly = 0) { reportNetworkIssue() }
        coVerify(exactly = 0) { cacheDB.fetch() }

        coVerify { clearNetworkIssue() }
        coVerify { cacheDB.write(any()) }
    }
}
