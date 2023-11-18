package com.github.bkmbigo.solitaire.data.ktor.utils

/** A result for a network call */
sealed class NetworkResult<T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error<T>(val errorCode: Int, val errorMessage: String) : NetworkResult<T>()

    fun dataOrNull() =
        when (this) {
            is Error -> null
            is Success -> data
        }

    fun dataOrElse(fn: () -> T) = when (this) {
        is Error -> fn()
        is Success -> data
    }

}

