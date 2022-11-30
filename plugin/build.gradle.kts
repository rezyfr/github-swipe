plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compileOnly(gradleApi())
}

tasks {
    compileKotlin { kotlinOptions.jvmTarget = "1.8" }
}

gradlePlugin {
    (plugins) {
        register("plugin") {

            id = "io.rezyfr.gradle.plugin"

            implementationClass = "io.rezyfr.plugin.Plugin"
        }

    }
}

