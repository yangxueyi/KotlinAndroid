package com.xueyi.yang.kotlinandroid.module.login

import android.os.Bundle
import android.view.View
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.base.BaseActivity
import com.xueyi.yang.kotlinandroid.constant.Constant
import com.xueyi.yang.kotlinandroid.utils.SpUtils
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by YangXueYi
 * Time : 2018/1/23.
 */
class LoginActivity :BaseActivity(){


    /*拿到是否已经登录*/
    private var isLogin : Boolean by SpUtils(Constant.LOGIN_KEY,false)
    /*拿到用户名*/
    private var user : String by SpUtils(Constant.USERNAME_KEY,"")
    /*拿到密码*/
    private var psw : String by SpUtils(Constant.PASSWORD_KEY,"")

    override fun initLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_login)
    }

    override fun init() {
        btn_register.setOnClickListener(onClickListener)
        btn_login.setOnClickListener(onClickListener)
        iv_login_exit.setOnClickListener(onClickListener)

    }

    override fun initAdapter() {
    }


    private val onClickListener = View.OnClickListener{
//        view ->

    }
}