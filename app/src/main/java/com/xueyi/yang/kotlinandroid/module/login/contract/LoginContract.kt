package com.xueyi.yang.kotlinandroid.module.login.contract

import com.xueyi.yang.kotlinandroid.base.contract.BaseContract
import com.xueyi.yang.kotlinandroid.base.presenter.BasePresenter
import com.xueyi.yang.kotlinandroid.bean.LoginResponse

/**
 * Created by YangXueYi
 * Time : 2018/2/5.
 */
class LoginContract {


    interface RigisterCallback{
        fun registerSuccess(result : LoginResponse)
        fun registerFailed(str : String)
    }

    interface LoginCallback{
        fun loginSucess(result : LoginResponse)
        fun loginFailed(str : String)
    }


    interface mModel{
        fun onRigisterResponse(rigisterCallback: RigisterCallback, userName: String, password: String, repassword: String)
        fun onLoginResponse(loginCallback: LoginCallback, userName : String, password : String)
    }


    interface LoginView : BaseContract.BaseView{
        fun onRegisterSuccess(result : LoginResponse)
        fun onRegisterFailed(str : String)
        fun onLoginSucess(result : LoginResponse)
        fun onLoginFailed(str : String)
    }


    interface mPresenter : BasePresenter{
        fun registerResult(userName : String, password : String, repassword: String)
        fun loginResult(userName : String, password : String)
    }
}