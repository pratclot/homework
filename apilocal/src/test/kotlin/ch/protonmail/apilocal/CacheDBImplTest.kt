package ch.protonmail.apilocal

import ch.protonmail.apilocal.util.mknew
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import java.io.File

class CacheDBImplTest {

    private val file = File.createTempFile("tmp", "tmp")
    private val cacheDBImpl = CacheDBImpl(file)

    @Test
    fun `CacheDBImpl can retrieve original content`() = runTest {
        mknew().let {
            cacheDBImpl.write(it)

            Assert.assertEquals(it, cacheDBImpl.fetch())
        }
    }
}
