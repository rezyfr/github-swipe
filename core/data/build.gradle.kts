import io.rezyfr.plugin.Plugin
import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android-extensions")
    id("io.rezyfr.gradle.plugin")
    id("com.google.dagger.hilt.android")
}

android {
    compileSdk = Plugin.configVersion.compileSdk
    defaultConfig {
        minSdk = Plugin.configVersion.minSdk
        targetSdk = Plugin.configVersion.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField("String", "APP_TYPE", String.format("\"%s\"", "debug"))
            enableUnitTestCoverage = true
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    flavorDimensions.add("api")
    productFlavors {
        create("development") {
            dimension = "api"
        }
        create("production") {
            dimension = "api"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(project(Plugin.modules.domain))

    implementation(Plugin.hilt.android)
    kapt(Plugin.hilt.compiler)

    debugImplementation(Plugin.networking.chuckLibrary)
    releaseImplementation(Plugin.networking.chuckLibraryNoOp)

    Plugin.coroutine.implementation.forEach { implementation(it) }
    Plugin.networking.implementation.forEach { implementation(it) }
}

task("printVersionName") {
    doLast {
        println("${android.defaultConfig.versionName}")
    }
}