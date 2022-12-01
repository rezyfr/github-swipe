package io.rezyfr.githubswipe.data.repository

import io.rezyfr.githubswipe.data.network.GithubService
import io.rezyfr.githubswipe.data.utils.toFetchDataDomain
import io.rezyfr.githubswipe.domain.model.UserItem
import io.rezyfr.githubswipe.domain.repository.GithubRepository
import io.rezyfr.githubswipe.domain.utils.FetchData

class GithubRepositoryImpl(
    private val service: GithubService
) : GithubRepository{
    override suspend fun search(query: String, page: Int, perPage: Int): FetchData<List<UserItem>> {
        val response = service.search(query, page, perPage).toFetchDataDomain { response ->
            response.items?.map { user ->
                user.toDomain()
            } ?: listOf()
        }
        return if (response is FetchData.Success && response.data.isEmpty()) FetchData.Empty
        else response
    }
}