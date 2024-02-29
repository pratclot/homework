package buildtools

import buildtools.util.addImpl
import buildtools.util.addKapt
import buildtools.util.addLib
import buildtools.util.addTestImpl
import buildtools.util.addTestRuntimeOnly
import buildtools.util.libs
import com.android.build.gradle.AppExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension

class CommonPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run proj@{
            plugins.run {
                apply("kotlin-kapt")
                /**
                 * TODO not sure if all projects need that
                 */
                apply("org.jetbrains.kotlin.plugin.serialization")
                /**
                 * TODO it would look better in the version catalog
                 */
                apply("org.jlleitschuh.gradle.ktlint")
            }
            extensions.getByType(JavaPluginExtension::class.java).run {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
            dependencies.run {
                addImpl("kotlin.stdlib")
                addImpl("kotlin.coroutines")

                addTestImpl("junit")
                addTestImpl("mockk")
                addTestImpl("kotlin.coroutines.test")

                if (name != "commontest") addTestImpl(project(":commontest"))
                if (name != "common") addImpl(project(":common"))
            }
        }
    }
}