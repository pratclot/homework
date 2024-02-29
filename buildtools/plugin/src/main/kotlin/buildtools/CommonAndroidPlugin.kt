package buildtools

import buildtools.util.addImpl
import buildtools.util.addKapt
import org.gradle.api.Plugin
import org.gradle.api.Project


class CommonAndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            plugins.run {
                apply("org.jetbrains.kotlin.android")

                apply("kotlin-android")
                apply("com.google.dagger.hilt.android")

                apply("buildtools.common")
            }
            dependencies.run {
                addImpl("hilt.android")
                addKapt("hilt.android.compiler")
                addImpl("timber")
                addImpl(project(":usecase"))
            }
        }
    }
}