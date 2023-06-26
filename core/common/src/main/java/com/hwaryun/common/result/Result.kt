package com.hwaryun.common.result

sealed class DataResult<out T>(val value: T? = null, val throwable: Throwable? = null) {
    class Success<T>(data: T) : DataResult<T>(data)
    class Failure<T>(throwable: Throwable?) : DataResult<T>(throwable = throwable)
}

sealed class UiResult<out T>(val value: T? = null, val throwable: Throwable? = null) {
    class Loading<T>(data: T? = null) : UiResult<T>(data)
    class Success<T>(data: T) : UiResult<T>(data)
    class Empty<T>(data: T? = null) : UiResult<T>(data)
    class Failure<T>(throwable: Throwable?) : UiResult<T>(throwable = throwable)
}