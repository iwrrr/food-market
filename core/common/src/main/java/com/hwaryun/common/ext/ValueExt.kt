package com.hwaryun.common.ext

import android.util.Patterns
import timber.log.Timber
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

fun Int?.toNumberFormat(): String {
    return try {
        this?.let {
            val format = NumberFormat.getInstance().apply {
                maximumFractionDigits = 0
            }
            format.format(this)
        } ?: "0"
    } catch (e: Exception) {
        Timber.e(e, "ERROR ====> ${e.localizedMessage}")
        "0"
    }
}

fun Long.convertUnixToDate(pattern: String): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(date)
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