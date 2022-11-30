import io.rezyfr.plugin.Plugin

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android-extensions")
    id("io.rezyfr.gradle.plugin")
    id("dagger.hilt.android.plugin")
}

fun buildProperty(key: String, format: Boolean = false): String {
    return if (format) {
        String.format("\"%s\"", project.property(key) as String)
    } else {
        project.property(key) as String
    }
}
android {
    compileSdk = Plugin.configVersion.compileSdk

    defaultConfig {
        applicationId = Plugin.configVersion.applicationId
        minSdk = Plugin.configVersion.minSdk
        targetSdk = Plugin.configVersion.targetSdk
        versionCode = Plugin.configVersion.versionCode
        versionName = Plugin.configVersion.versionName
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "BASE_URL", buildProperty("BASE_RELEASE_URL"))
        }
        debug {
            isTestCoverageEnabled = false
            isDebuggable = true
            buildConfigField("String", "BASE_URL", buildProperty("BASE_DEBUG_URL"))
        }
    }

    compileOptions {
        sourceCompatibility=  JavaVersion.VERSION_11
        targetCompatibility=  JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-alpha08"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(Plugin.androidX.core)
    implementation(Plugin.lifeCycle.runtime)
    implementation(Plugin.hilt.android)
    implementation(Plugin.hilt.composeNavigation)
    kapt(Plugin.hilt.compiler)
    Plugin.compose.implementation.forEach {
        implementation(it)
    }
    Plugin.coroutine.implementation.forEach {
        implementation(it)
    }
}