package io.rezyfr.githubswipe.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.rezyfr.githubswipe.data.NetworkConfig
import io.rezyfr.githubswipe.provider.AppNetworkProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppProviderModule {
    @Provides
    @Singleton
    fun provideNetworkConfig() : NetworkConfig {
        return AppNetworkProvider()
    }
}
