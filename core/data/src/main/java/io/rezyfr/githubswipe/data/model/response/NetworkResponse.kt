package io.rezyfr.githubswipe.data.response

import io.rezyfr.githubswipe.data.model.response.ErrorResponse
import io.rezyfr.githubswipe.domain.utils.FetchData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Headers
import java.io.IOException

sealed class NetworkResponse<out T, out U> {
    /**
     * A request that resulted in a response with a 2xx status code that has a body.
     */
    data class Success<T>(
        val body: T,
        val headers: Headers? = null,
        val code: Int
    ) : NetworkResponse<T, Nothing>() {
        companion object {
            fun <T> getDummy(body: T) = Success(body = body, headers = null, code = 0)
        }
    }

    /**
     * A request that resulted in a response with a non-2xx status code.
     */
    data class ServerError<U>(
        val body: U?,
        val code: Int,
        val headers: Headers? = null
    ) : NetworkResponse<Nothing, U>()

    /**
     * A request that didn't result in a response.
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()

    /**
     * A request that resulted in an error different from an IO or Server error.
     *
     * An example of such an error is JSON parsing exception thrown by a serialization library.
     */
    data class UnknownError(
        val error: Throwable,
        val code: Int? = null,
        val headers: Headers? = null
    ) : NetworkResponse<Nothing, Nothing>()
}


fun <R, T> fetchNetwork(
    fetch: suspend () -> NetworkResponse<T, ErrorResponse>,
    onSuccess: suspend (T) -> R
): Flow<FetchData<R>> = flow {
    emit(FetchData.Loading)
    when (val response = fetch()) {
        is NetworkResponse.Success -> {
            emit(FetchData.Success(onSuccess(response.body)))
        }
        is NetworkResponse.ServerError -> emit(
            FetchData.Error(
                response.body?.message.orEmpty(),
                response.code
            )
        )
        is NetworkResponse.NetworkError -> emit(FetchData.Error(response.error.message.orEmpty()))
        is NetworkResponse.UnknownError -> emit(
            FetchData.Error(
                response.error.message.orEmpty(),
                response.code
            )
        )
    }
}.flowOn(Dispatchers.IO)

fun <R, T> fetchNetworkWithEmit(
    fetch: suspend () -> NetworkResponse<T, ErrorResponse>,
    emitOnSuccess: suspend (T) -> FetchData<R>
): Flow<FetchData<R>> = flow {
    emit(FetchData.Loading)
    when (val response = fetch()) {
        is NetworkResponse.Success -> {
            emit(emitOnSuccess(response.body))
        }
        is NetworkResponse.ServerError -> emit(FetchData.Error(response.body?.message.orEmpty()))
        is NetworkResponse.NetworkError -> emit(FetchData.Error(response.error.message.orEmpty()))
        is NetworkResponse.UnknownError -> emit(FetchData.Error(response.error.message.orEmpty()))
    }
}.flowOn(Dispatchers.IO)