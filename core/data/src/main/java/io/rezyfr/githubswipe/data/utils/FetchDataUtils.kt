package io.rezyfr.githubswipe.data.utils

import io.rezyfr.githubswipe.data.model.response.ErrorResponse
import io.rezyfr.githubswipe.data.response.NetworkResponse
import io.rezyfr.githubswipe.domain.utils.FetchData

fun <T, A> NetworkResponse<T, ErrorResponse>.toFetchDataDomain(toDomain: (T) -> A): FetchData<A> {
    return when (this) {
        is NetworkResponse.Success -> FetchData.Success(toDomain(body))
        is NetworkResponse.ServerError -> FetchData.Error(
            body?.message.orEmpty(),
            body?.code
        )
        is NetworkResponse.NetworkError -> FetchData.Error(error.message.orEmpty())
        is NetworkResponse.UnknownError -> FetchData.Error(error.message.orEmpty(), code)
    }
}