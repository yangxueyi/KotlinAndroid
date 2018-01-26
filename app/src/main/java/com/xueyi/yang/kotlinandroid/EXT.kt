package com.xueyi.yang.kotlinandroid

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.cancelAndJoin

/**
 * Created by YangXueYi
 * Time : 2018/1/25.
 */


/**
 * 取消协程
 */
suspend fun Deferred<Any>?.cancelAndJoinByActive() = this?.run {
    if (isActive) {
        cancelAndJoin()
    }
}

fun Deferred<Any>?.cancelByActive() = this?.run {
    if (isActive) {
        cancel()
    }
}