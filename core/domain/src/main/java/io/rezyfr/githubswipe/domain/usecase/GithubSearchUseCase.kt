package io.rezyfr.githubswipe.domain.usecase

import io.rezyfr.githubswipe.domain.model.UserItem
import io.rezyfr.githubswipe.domain.repository.GithubRepository
import io.rezyfr.githubswipe.domain.utils.FetchData
import kotlinx.coroutines.CoroutineDispatcher

class GithubSearchUseCase(
    private val repository: GithubRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(param: GithubSearchUseCaseParam): FetchData<List<UserItem>> {
        val (query, page, perPage) = param
        return repository.search(query, page, perPage)
    }
}

data class GithubSearchUseCaseParam(
    val query: String,
    val page: Int,
    val perPage: Int
)