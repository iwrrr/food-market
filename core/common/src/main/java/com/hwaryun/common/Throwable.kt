package com.hwaryun.common

data class NetworkClientException(override val message: String? = null): Throwable()
class UnexpectedValuesRepresentation: Throwable()
class FieldErrorException(val errorFields: List<Pair<Int, Int>>) : Throwable()