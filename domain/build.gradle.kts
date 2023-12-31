plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.io.gitlab.arturbosch.detekt)
    alias((libs.plugins.org.jlleitschuh.gradle.ktlint))
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.paging.common)

    // DETEKT
    detektPlugins(libs.detetkcompose)
    detektPlugins(libs.detetkformatting)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
