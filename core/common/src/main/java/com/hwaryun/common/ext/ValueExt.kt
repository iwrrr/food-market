package com.hwaryun.common.ext

import android.util.Patterns

val CharSequence?.isValid: Boolean
    get() = if (this.isNullOrEmpty()) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

fun String?.orDash(): String {
    return this ?: "-"
}

fun Int?.toBoolean(): Boolean {
    return if (this == null) {
        false
    } else {
        this != 0
    }
}

fun Boolean?.orFalse(): Boolean {
    return this ?: false
}

fun String?.orZero(): Int {
    return this?.toInt() ?: 0
}

fun Int?.orZero(): Int {
    return this ?: 0
}

fun Long?.orZero(): Long {
    return this ?: 0
}

fun Double?.orZero(): Double {
    return this ?: 0.0
}

fun String?.isNullorDash(): Boolean {
    return !(this.isNullOrEmpty() || this == "-")
}

fun Any?.orFalse(): Boolean {
    return this != null
}

fun <T> List<T>.isEmpty(callback: (List<T>) -> Unit) {
    if (this.isEmpty()) callback.invoke(this)
}

fun <T> List<T>.isNotEmpty(callback: (List<T>) -> Unit) {
    if (this.isNotEmpty()) callback.invoke(this)
}

fun <T> List<T>.mapWhenNotEmpty(callback: (T) -> Unit) {
    if (this.isNotEmpty()) {
        this.map {
            callback.invoke(it)
        }
    }
}