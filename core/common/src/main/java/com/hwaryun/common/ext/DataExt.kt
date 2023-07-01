package com.hwaryun.common.ext

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.hwaryun.common.ConnectivityException
import com.hwaryun.common.NetworkClientException
import com.hwaryun.common.UnexpectedValuesRepresentation
import com.hwaryun.common.http.infrastructure.BaseResponse
import com.hwaryun.common.result.DataResult
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException

suspend fun <T> execute(block: suspend () -> T): DataResult<T> {
    return try {
        DataResult.Success(block.invoke())
    } catch (throwable: Throwable) {

        when (throwable) {
            /**
             * Handle http status code can reference to
             * https://developer.mozilla.org/en-US/docs/Web/HTTP/Status#client_error_responses
             * */
            is HttpException -> {
                when (throwable.code()) {
                    in 300..399 -> {
                        DataResult.Failure(
                            NetworkClientException(
                                getErrorMessageFromApi(
                                    throwable.response()?.errorBody()
                                )
                            )
                        )
                    }

                    in 400..499 -> {
                        DataResult.Failure(
                            NetworkClientException(
                                getErrorMessageFromApi(
                                    throwable.response()?.errorBody()
                                )
                            )
                        )
                    }

                    in 500..599 -> {
                        DataResult.Failure(
                            NetworkClientException(
                                getErrorMessageFromApi(
                                    throwable.response()?.errorBody()
                                )
                            )
                        )
                    }

                    else -> {
                        DataResult.Failure(UnexpectedValuesRepresentation())
                    }
                }
            }

            is ConnectException -> {
                DataResult.Failure(ConnectivityException())
            }

            else -> {
                DataResult.Failure(throwable)
            }
        }
    }
}

suspend fun <T> proceed(block: suspend () -> T): DataResult<T> {
    return try {
        DataResult.Success(block.invoke())
    } catch (throwable: Throwable) {
        DataResult.Failure(throwable)
    }
}

fun <T> getErrorMessageFromApi(response: T): String {
    val responseBody = response as ResponseBody
    return try {
        val gson = Gson()
        val body = gson.fromJson(responseBody.string(), BaseResponse::class.java)
        Timber.e("ERROR ====> ${body.meta?.message}")
        body.meta?.message ?: "Error Api"
    } catch (e: JsonParseException) {
        Timber.e(e, "ERROR ====> ${e.message}")
        "Error Api"
    }
}