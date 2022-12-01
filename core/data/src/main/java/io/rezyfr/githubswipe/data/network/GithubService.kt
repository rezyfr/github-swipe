package io.rezyfr.githubswipe.data.network

import io.rezyfr.githubswipe.data.model.response.ErrorResponse
import io.rezyfr.githubswipe.data.model.response.GithubUserResponse
import io.rezyfr.githubswipe.data.response.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("/search/users")
    suspend fun search(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): NetworkResponse<GithubUserResponse, ErrorResponse>
}