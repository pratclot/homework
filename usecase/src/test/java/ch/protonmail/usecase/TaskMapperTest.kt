package ch.protonmail.usecase

import ch.protonmail.cryptowrapper.CryptoWrapper
import ch.protonmail.usecase.di.createDateTimeFormatter
import ch.protonmail.usecase.util.CryptoWrapperFake
import ch.protonmail.usecase.util.mknew
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class TaskMapperTest {

    private val cryptoWrapper: CryptoWrapper = CryptoWrapperFake
    private val dateTimeFormatter = createDateTimeFormatter()
    private val taskMapper: TaskMapper = TaskMapper(cryptoWrapper, dateTimeFormatter)

    @Test
    fun `mapper handles various datetime inputs`() = runTest {
        values().forEach { (inputDate, expectedDate) ->
            taskMapper.run { mknew(creationDate = inputDate, dueDate = inputDate).toUI() }
                .let { Assert.assertEquals(expectedDate, it.creationDate) }
        }
    }

    private fun values() = listOf(
        "2019-09-01T12:00:00.511Z" to "2019-09-01 • 12:00",
        "2019-09-01T12:00:00.511Y" to E_INVALID_DATE
    )
}
