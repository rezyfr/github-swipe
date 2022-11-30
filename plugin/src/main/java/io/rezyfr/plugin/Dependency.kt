package io.rezyfr.plugin

object AndroidX {
    const val core = "androidx.core:core-ktx:${Versions.core}"
}

object Compose {
    const val activity = "androidx.activity:activity-compose:1.4.0"
    const val material = "androidx.compose.material:material:${Versions.compose}"
    const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
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
    const val runtime=  "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
}

object DaggerHilt {
    const val android = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
    const val compiler = "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"
    const val composeNavigation = "androidx.hilt:hilt-navigation-compose:1.0.0"
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
    const val rxjava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val implementation = arrayOf(
        core,
        okhttp,
        gson,
        rxjava
    )
}

object Coil {
    const val core = "io.coil-kt:coil:${Versions.coil}"
    const val gif = "io.coil-kt:coil-gif:${Versions.coil}"
    val implementation = arrayOf(
        core,
        gif
    )
}

object Chucker {
    const val release = "com.github.chuckerteam.chucker:library-no-op:${Versions.chucker}"
    const val debug = "com.github.chuckerteam.chucker:library:${Versions.chucker}"
}

object Timber {
    const val core = "com.jakewharton.timber:timber:${Versions.timber}"
}

object Module {
    const val data = ":core:data"
    const val domain = ":core:domain"

    val implementation = listOf<String>(data, domain)
}

