package io.rezyfr.plugin

import AndroidConfigVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class Plugin : Plugin<Project> {

    override fun apply(project: Project) {
        // Possibly common dependencies or can stay empty

    }

    companion object {
        val versions = Versions
        val configVersion = AndroidConfigVersion
        val androidX = AndroidX
        val networking = Networking
        val coroutine = Coroutine
        val lifeCycle = LifeCycle
        val hilt = DaggerHilt
        val testDependencies = Test
        val modules = Module
        val compose = Compose
    }
}
