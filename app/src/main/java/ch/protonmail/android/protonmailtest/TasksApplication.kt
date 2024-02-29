package ch.protonmail.android.protonmailtest

import android.app.Application
import ch.protonmail.common.AppConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class TasksApplication : Application() {

    @Inject
    lateinit var appConfig: AppConfig

    override fun onCreate() {
        super.onCreate()

        if (appConfig.debug) Timber.plant(Timber.DebugTree())
    }
}
