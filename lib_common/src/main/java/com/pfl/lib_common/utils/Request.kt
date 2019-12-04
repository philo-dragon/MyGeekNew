package com.pfl.lib_common.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

infix fun Any.start(start: (() -> Unit)): Any {
    start()
    return this
}

infix fun <T> Any.request(loader: suspend () -> T): Deferred<T> {
    return GlobalScope.async(Dispatchers.IO, start = CoroutineStart.LAZY) {
        loader()
    }
}

fun <T> Deferred<T>.then(onSuccess: suspend (T) -> Unit, onError: suspend (String) -> Unit, onComplete: (() -> Unit)? = null): Job {
    return GlobalScope.launch(context = Main) {
        try {
            val result = this@then.await()
            onSuccess(result)
        } catch (e: Exception) {
            e.printStackTrace()
            when (e) {
                is UnknownHostException -> onError("network is error!")
                is TimeoutException -> onError("network is error!")
                is SocketTimeoutException -> onError("network is error!")
                else -> onError("network is error!")
            }
        }finally {
            onComplete?.invoke()
        }
    }
}