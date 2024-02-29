package ch.protonmail.apilocal.di

import ch.protonmail.apilocal.CacheDB
import ch.protonmail.apilocal.CacheDBImpl
import ch.protonmail.common.OKHTTP_CACHE_DIR
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Named

private const val OKHTTP_CACHE_FILE = "OKHTTP_CACHE_FILE"

@Module
@InstallIn(SingletonComponent::class)
class CacheModule {

    @Named(OKHTTP_CACHE_FILE)
    @Provides
    fun provideCacheFile(@Named(OKHTTP_CACHE_DIR) dir: File): File = dir.run {
        File(dir.path, OKHTTP_CACHE_FILE).also { it.createNewFile() }
    }

    @Provides
    fun provideCacheDB(@Named(OKHTTP_CACHE_FILE) file: File): CacheDB = CacheDBImpl(file)
}
