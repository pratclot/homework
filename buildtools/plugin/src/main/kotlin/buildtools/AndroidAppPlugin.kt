package buildtools

import buildtools.util.libs
import com.android.build.gradle.AppExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidAppPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            plugins.run {
                apply("com.android.application")
                apply("buildtools.commonAndroid")
            }
            extensions.getByType(AppExtension::class.java).run {
                compileOptions.run {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                defaultConfig.run {
                    minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
                    targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
                    compileSdkVersion = "android-" + libs.findVersion("compileSdk").get().requiredVersion

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
            }
        }
    }
}