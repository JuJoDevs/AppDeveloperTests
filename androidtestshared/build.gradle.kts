plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.io.gitlab.arturbosch.detekt)
    alias((libs.plugins.org.jlleitschuh.gradle.ktlint))
}

android {
    namespace = "com.jujodevs.appdevelopertests"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":app"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":usecases"))
    implementation(project(":testshared"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.paging.common)
    implementation(libs.paging.testing)
    implementation(libs.javax.inject)

    // DETEKT
    detektPlugins(libs.detetkcompose)
    detektPlugins(libs.detetkformatting)
}
