package com.hwaryun.common.ext

import com.hwaryun.common.result.DataResult
import com.hwaryun.common.result.UiResult

fun <T> UiResult<T>.subscribe(
    doOnSuccess: ((resource: UiResult<T>) -> Unit)? = null,
    doOnError: ((resource: UiResult<T>) -> Unit)? = null,
    doOnLoading: ((resource: UiResult<T>) -> Unit)? = null,
    doOnEmpty: ((resource: UiResult<T>) -> Unit)? = null,
) {
    when (this) {
        is UiResult.Success -> doOnSuccess?.invoke(this)
        is UiResult.Failure -> doOnError?.invoke(this)
        is UiResult.Loading -> doOnLoading?.invoke(this)
        is UiResult.Empty -> doOnEmpty?.invoke(this)
    }
}

suspend fun <T> UiResult<T>.suspendSubscribe(
    doOnSuccess: (suspend (resource: UiResult<T>) -> Unit)? = null,
    doOnError: (suspend (resource: UiResult<T>) -> Unit)? = null,
    doOnEmpty: (suspend (resource: UiResult<T>) -> Unit)? = null,
    doOnLoading: (suspend (resource: UiResult<T>) -> Unit)? = null,
) {
    when (this) {
        is UiResult.Success -> doOnSuccess?.invoke(this)
        is UiResult.Failure -> doOnError?.invoke(this)
        is UiResult.Loading -> doOnLoading?.invoke(this)
        is UiResult.Empty -> doOnEmpty?.invoke(this)
    }
}

suspend fun <T> DataResult<T>.suspendSubscribe(
    doOnSuccess: suspend (resource: DataResult<T>) -> Unit,
    doOnError: suspend (resource: DataResult<T>) -> Unit,
) {
    when (this) {
        is DataResult.Success -> doOnSuccess.invoke(this)
        is DataResult.Failure -> doOnError.invoke(this)
    }
}
