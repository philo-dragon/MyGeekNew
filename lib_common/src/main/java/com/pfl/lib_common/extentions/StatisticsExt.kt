package com.pfl.lib_common.extentions

fun statisticsMethodRunTime(block: () -> Unit) {
    val startTime = System.currentTimeMillis()
    block()
    val endTime = System.currentTimeMillis()
    println("${block.javaClass.simpleName} === ${endTime - startTime}")
}
