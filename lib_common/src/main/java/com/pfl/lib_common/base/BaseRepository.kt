package com.pfl.lib_common.base

open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> HttpResponse<T>): HttpResponse<T> {
        return call.invoke()
    }
}