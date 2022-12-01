package io.rezyfr.githubswipe.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.rezyfr.githubswipe.domain.repository.GithubRepository
import io.rezyfr.githubswipe.domain.usecase.GithubSearchUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {
    @Provides
    @Named(value = "IoDispatchers")
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @ViewModelScoped
    @Provides
    fun provideSearchUseCase(
        repository: GithubRepository,
        @Named(value = "IoDispatchers")
        coroutineDispatcher: CoroutineDispatcher
    ) = GithubSearchUseCase(repository, coroutineDispatcher)
}