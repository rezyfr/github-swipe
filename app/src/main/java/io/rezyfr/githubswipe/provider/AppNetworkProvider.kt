package io.rezyfr.githubswipe.provider

import io.rezyfr.githubswipe.BuildConfig
import io.rezyfr.githubswipe.data.NetworkConfig

class AppNetworkProvider : NetworkConfig() {
    override fun baseUrl(): String {
        return BuildConfig.BASE_URL
    }

    override fun isDev(): Boolean {
        return BuildConfig.DEBUG
    }
}