package com.pfl.lib_common.base

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParseException
import com.pfl.lib_common.exception.ApiException
import com.pfl.lib_common.exception.NoNetworkException
import com.pfl.lib_common.utils.TestUtil.*
import kotlinx.coroutines.*
import org.json.JSONException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException


open class BaseViewModel : ViewModel(), LifecycleObserver {

    fun launch(tryBlock: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { tryCatch(tryBlock, {}, {}) }
    }

    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Exception) {
                if (e !is CancellationException) {
                    catchBlock(e)
                    onError(e)
                } else {
                    throw e
                }
            } finally {
                finallyBlock()
            }
        }
    }

    suspend fun executeResponse(
        response: HttpResponse<Any>
        , successBlock: suspend CoroutineScope.() -> Unit
        , errorBlock: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            if (response.errorCode == -1) errorBlock()
            else successBlock()
        }
    }

    private fun onError(e: Throwable) {
        val message = e.message
        if (e is HttpException) {     //   HTTP错误
            onException(BAD_NETWORK, message)
        } else if (e is NoNetworkException) {// 没有网络
            onException(NOT_NETWORK, message)
        } else if (e is ConnectException || e is UnknownHostException) {   //   连接错误
            onException(CONNECT_ERROR, message)
        } else if (e is InterruptedIOException) {   //  连接超时
            onException(CONNECT_TIMEOUT, message)
        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException
        ) {   //  解析错误
            onException(PARSE_ERROR, message)
        } else if (e is ApiException) { // 自定义异常
            onException(API_ERROR, message)
        } else {
            onException(UNKNOWN_ERROR, message)
        }
    }

    @SuppressLint("SwitchIntDef")
    fun onException(@ErrrorType type: Int, message: String?) {
        when (type) {
            PARSE_ERROR,
            BAD_NETWORK,
            CONNECT_ERROR,
            CONNECT_TIMEOUT,
            NOT_NETWORK -> {
                println("没有网络")
            }
            API_ERROR -> {
                println("接口异常")
            }
            else -> {
                println("未知错误")
            }
        }
    }

}