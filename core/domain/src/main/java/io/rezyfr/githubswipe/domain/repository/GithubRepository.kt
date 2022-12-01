package io.rezyfr.githubswipe.domain.repository

import io.rezyfr.githubswipe.domain.model.UserItem
import io.rezyfr.githubswipe.domain.utils.FetchData

interface GithubRepository {
    suspend fun search(query: String, page: Int, perPage: Int): FetchData<List<UserItem>>
}