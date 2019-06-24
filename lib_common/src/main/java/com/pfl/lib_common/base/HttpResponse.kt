package com.pfl.lib_common.base

data class HttpResponse<out T>(val errorCode: Int = 0, val errorMsg: String, val data: T)