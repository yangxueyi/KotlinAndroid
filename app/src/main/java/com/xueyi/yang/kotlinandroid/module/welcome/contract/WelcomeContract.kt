package com.xueyi.yang.kotlinandroid.module.welcome.contract

import com.xueyi.yang.kotlinandroid.base.contract.BaseContract
import com.xueyi.yang.kotlinandroid.base.presenter.BasePresenter

/**
 * Created by YangXueYi
 * Time : 2018/1/19.
 */
class WelcomeContract {


    interface view :BaseContract.BaseView{
        fun onSkipActivity()//跳转页面
    }

    interface presenter :BasePresenter{
        fun skipActivity()//跳转页面
    }
}