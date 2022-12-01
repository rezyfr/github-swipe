package io.rezyfr.githubswipe.data

abstract class NetworkConfig {
    abstract fun baseUrl(): String
    abstract fun isDev() : Boolean
}