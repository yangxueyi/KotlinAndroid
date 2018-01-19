package com.xueyi.yang.kotlinandroid.module.welcome.presenter

import com.xueyi.yang.kotlinandroid.module.welcome.contract.WelcomeContract

/**
 * Created by YangXueYi
 * Time : 2018/1/19.
 */
class WelcomePresenter(private val welcomView : WelcomeContract.view) : WelcomeContract.presenter {

    override fun showSuccess() {

    }

    override fun showError() {

    }

    override fun skipActivity() {
        welcomView.onSkipActivity()
    }
}