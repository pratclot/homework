package ch.protonmail.android.protonmailtest.di

import android.content.Context
import ch.protonmail.android.crypto.CryptoLib
import ch.protonmail.android.protonmailtest.BuildConfig
import ch.protonmail.android.protonmailtest.crypto.CryptoWrapperImpl
import ch.protonmail.android.protonmailtest.util.settingsDataStore
import ch.protonmail.common.AppConfig
import ch.protonmail.common.AppLogger
import ch.protonmail.common.DownloadTracker
import ch.protonmail.common.OKHTTP_CACHE_DIR
import ch.protonmail.cryptowrapper.CryptoWrapper
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.File
import javax.inject.Named

private const val QWERTY = "QWERTY"
private const val FILE_IMAGE_CACHE = "image_cache"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Named(OKHTTP_CACHE_DIR)
    @Provides
    fun provideOkHttpCacheDir(@ApplicationContext context: Context): File = context.cacheDir

    @Provides
    fun provideAppConfig(): AppConfig = object : AppConfig {
        override val debug: Boolean
            get() = BuildConfig.DEBUG
    }

    @Provides
    fun provideAppLogger() = object : AppLogger {
        override fun d(msg: String) {
            Timber.d("$QWERTY $msg")
        }

        override fun e(msg: String) {
            Timber.e("$QWERTY $msg")
        }
    }

    @Provides
    fun provideCryptoWrapper(cryptoWrapperImpl: CryptoWrapperImpl): CryptoWrapper =
        cryptoWrapperImpl

    @Provides
    fun provideCryptoLib(): CryptoLib = CryptoLib()

    @Provides
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader =
        ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve(FILE_IMAGE_CACHE))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()

    @Provides
    fun provideDownloadTracker(
        @ApplicationContext context: Context
    ): DownloadTracker = object : DownloadTracker {
        private val datastore = context.settingsDataStore

        override val downloadedTasks: Flow<Map<String, Boolean>>
            get() = datastore.data.map { it.taskEntryMap }

        override suspend fun markAsDownloaded(id: String) {
            datastore.updateData {
                it.toBuilder()
                    .putTaskEntry(id, true)
                    .build()
            }
        }
    }
}
