package buildtools

import buildtools.util.addImpl
import buildtools.util.addKapt
import buildtools.util.addLib
import buildtools.util.libs
import com.android.build.gradle.AppExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension


class KotlinLibPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run proj@{
            plugins.run {
                apply("java-library")
                apply("org.jetbrains.kotlin.jvm")

                apply("buildtools.common")
            }
            extensions.getByType(JavaPluginExtension::class.java).run {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
            dependencies.run {
                addImpl("hilt.core")
                addKapt("hilt.compiler")
            }
        }
    }
}