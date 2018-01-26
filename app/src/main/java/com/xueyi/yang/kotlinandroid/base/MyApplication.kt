package com.xueyi.yang.kotlinandroid.base

import android.app.Application
import com.xueyi.yang.kotlinandroid.utils.SpUtils

/**
 * Created by YangXueYi
 * Time : 2018/1/19.
 */
class MyApplication :Application(){

    override fun onCreate() {
        super.onCreate()

        /*初始化sp*/
        SpUtils.setContext(applicationContext)

    }

}