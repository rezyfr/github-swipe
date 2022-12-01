package io.rezyfr.githubswipe.domain.utils

sealed class FetchData<out T> {
    object Uninitialized : FetchData<Nothing>()
    object Loading : FetchData<Nothing>()
    object Empty : FetchData<Nothing>()
    data class Success<T>(val data: T) : FetchData<T>()
    data class Error(val message: String, val code: Int? = null) :
        FetchData<Nothing>()

    fun successData(): T? = (this as? Success<T>)?.data
}