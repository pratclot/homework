plugins {
    id("buildtools.kotlinLib")
}

dependencies {
    implementation(project(":data"))
    implementation(libs.kotlin.serialization)
}
