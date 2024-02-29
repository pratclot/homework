plugins {
    id("buildtools.kotlinLib")
}

dependencies {
    implementation(project(":data"))
    implementation(project(":state"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.adapter)
    implementation(libs.retrofit.kotlin)
    implementation(libs.retrofit.logging)
    implementation(libs.kotlin.serialization)
}
