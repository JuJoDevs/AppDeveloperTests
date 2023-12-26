plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.io.gitlab.arturbosch.detekt)
    alias((libs.plugins.org.jlleitschuh.gradle.ktlint))
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.paging.common)
    implementation(libs.javax.inject)

    // DETEKT
    detektPlugins(libs.detetkcompose)
    detektPlugins(libs.detetkformatting)
}
