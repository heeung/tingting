package com.alsif.tingting.util

import retrofit2.Response

internal inline fun <T> handleApi(block: () -> Response<T>): T {
    return block.invoke().getValueOrThrow()
}