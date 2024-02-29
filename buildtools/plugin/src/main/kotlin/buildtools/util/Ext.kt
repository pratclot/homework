package buildtools.util

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

private const val IMPLEMENTATION = "implementation"
private const val KAPT = "kapt"
private const val TEST_IMPLEMENTATION = "testImplementation"
private const val TEST_RUNTIME_ONLY = "testRuntimeOnly"

/**
 * https://github.com/gradle/gradle/issues/25737
 */
val Project.libs: VersionCatalog
    get() = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

fun Project.addLib(configurationName: String, alias: String) {
    dependencies.run {
        addProvider(
            configurationName,
            project.provider { libs.findLibrary(alias).get().get() }
        )
    }
}

fun Project.addImpl(alias: String) = addLib(IMPLEMENTATION, alias)
fun Project.addKapt(alias: String) = addLib(KAPT, alias)
fun Project.addTestImpl(alias: String) = addLib(TEST_IMPLEMENTATION, alias)
fun Project.addTestRuntimeOnly(alias: String) = addLib(TEST_RUNTIME_ONLY, alias)

fun Project.addImpl(project: Project) {
    dependencies.run {
        add(IMPLEMENTATION, project)
    }
}
fun Project.addTestImpl(project: Project) {
    dependencies.run {
        add(TEST_IMPLEMENTATION, project)
    }
}
