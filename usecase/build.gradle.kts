plugins {
    id("buildtools.kotlinLib")
}

dependencies {
    implementation(project(":state"))
    implementation(project(":cryptowrapper"))
    implementation(project(":domain"))
    implementation(project(":uimodels"))
    implementation(project(":repository"))
}

/**
 * TODO This fixes absent factories in the :repository project when :app tries to make sense
 * of the Hilt graph in the presence of :repositoryboundary project. Unfortunately, it does
 * not make Hilt understand that the graph becomes complete :)
 */
//afterEvaluate {
//    tasks.getByName("kaptKotlin").dependsOn(":repository:kaptKotlin")
//}
