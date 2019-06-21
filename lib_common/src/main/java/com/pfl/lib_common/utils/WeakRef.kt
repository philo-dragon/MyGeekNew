package com.pfl.lib_common.utils

import java.lang.ref.WeakReference
import java.util.concurrent.CancellationException
import kotlin.coroutines.suspendCoroutine

/**
 * 封装弱引用类WeakRef，声明WeakReference类常量，
 * 将弱引用的对象any传入该常量构造函数。
 * 提供invoke方法返回弱引用的实例。
 */
class WeakRef<T> internal constructor(any: T) {

    private val weakRef = WeakReference(any)

    suspend operator fun invoke(): T {
        return suspendCoroutine {
            var rel = weakRef.get() ?: throw CancellationException()
            rel
        }
    }
}

fun <T : Any> T.weakReference() = WeakRef(this)