package com.xueyi.yang.kotlinandroid

import android.app.Activity
import android.content.Context
import android.support.annotation.IntegerRes
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.ChromeClientCallbackManager
import com.xueyi.yang.kotlinandroid.module.homeContent.HomeContentActivity
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.cancelAndJoin
import java.util.*

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

/**
 * 随机获取颜色
 * toHexString:返回16位的无符号整数
 * 16777215 是 FFFFFF
 */
fun getRandomColor() : String =  "#${Integer.toHexString((Math.random() * 16777215).toInt())}"


/*获取agentweb*/
fun String.getAgentWeb(activity: Activity,web_Content: ViewGroup,
                       layoutParams: LinearLayout.LayoutParams,
                       toolbarTitleCallback: ChromeClientCallbackManager.ReceivedTitleCallback) =
    AgentWeb.with(activity)//将web传入到关联界面
            .setAgentWebParent(web_Content, layoutParams)//传入web的父控件
            .useDefaultIndicator()//使用进度条
            .defaultProgressBarColor()//使用默认进度条颜色
            .setReceivedTitleCallback(toolbarTitleCallback)//设置 Web 页面的 title 回调
            .createAgentWeb()//创建
            .ready()//准备好了
            .go(this)!! //this：前面的String




fun Context.inflater(@LayoutRes resource: Int): View = LayoutInflater.from(this).inflate(resource, null)