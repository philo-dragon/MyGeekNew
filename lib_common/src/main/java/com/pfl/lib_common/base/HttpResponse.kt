package com.pfl.lib_common.base

data class HttpResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T)