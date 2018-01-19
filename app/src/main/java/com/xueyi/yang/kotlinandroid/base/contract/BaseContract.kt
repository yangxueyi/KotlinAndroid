package com.xueyi.yang.kotlinandroid.base.contract

/**
 * Created by YangXueYi
 * Time : 2018/1/17.
 */
interface BaseContract {

    interface BaseCallback{
        fun onNetworkError()
        fun onSuccess()
        fun onError()
    }

    interface BaseView{
        fun onShowToast(str : String)//toast
        fun onShowSuccess()//显示内容页面
        fun onShowError()//显示错误页面
    }

}