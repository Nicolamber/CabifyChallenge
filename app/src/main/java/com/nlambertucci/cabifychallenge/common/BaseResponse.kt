package com.nlambertucci.cabifychallenge.common

/**
 * Generic Base response to wrap all the api call responses.
 *
 * @param data contains all the information related to products.
 * @param message related to error message
 */
sealed class BaseResponse<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : BaseResponse<T>(data)
    class Error<T>(message: String?, data: T? = null) : BaseResponse<T>(data, message)
    class Loading<T>(data: T? = null) : BaseResponse<T>(data)
}