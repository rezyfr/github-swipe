package io.rezyfr.plugin

object AndroidX {
    const val core = "androidx.core:core-ktx:${Versions.core}"
}

object Compose {
    const val activity = "androidx.activity:activity-compose:1.4.0"
    const val material = "androidx.compose.material:material:${Versions.compose}"
    const val tooling = "androidx.compose.ui:ui-tooling:${Versions.composeUi}"
    const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
    val implementation = arrayOf(
        activity, material, tooling, runtime
    )
}

object Test {
    const val junit = "junit:junit:${Versions.junit}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val testEspresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val archTesting = "androidx.arch.core:core-testing:${Versions.testCore}"
}

object LifeCycle {
    const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
}

object DaggerHilt {
    const val android = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
    const val compiler = "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"
}

object Coroutine {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"

    val implementation = arrayOf(
        core, android
    )
}

object Networking {
    const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val chuckLibrary = "com.github.chuckerteam.chucker:library:${ThirdPartyLibVersions.chucker}"
    const val chuckLibraryNoOp = "com.github.chuckerteam.chucker:library-no-op:${ThirdPartyLibVersions.chucker}"
    val implementation = arrayOf(
        core,
        okhttp,
        gson,
        logging
    )
}


object ThirdPartyLibrary {
    const val core = "com.jakewharton.timber:timber:${Versions.timber}"
    const val landscapistCoil = "com.github.skydoves:landscapist-coil:1.3.7"
}

object Module {
    const val data = ":core:data"
    const val domain = ":core:domain"

    val implementation = listOf<String>(data, domain)
}

