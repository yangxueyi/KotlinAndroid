package com.xueyi.yang.kotlinandroid.module.login.presenter

import android.util.Log
import com.xueyi.yang.kotlinandroid.bean.LoginResponse
import com.xueyi.yang.kotlinandroid.module.login.contract.LoginContract
import com.xueyi.yang.kotlinandroid.module.login.model.LoginModel

/**
 * Created by YangXueYi
 * Time : 2018/2/5.
 */
class LoginPresenter(val mView : LoginContract.LoginView, private val mModel : LoginModel) : LoginContract.mPresenter {

    override fun registerResult(userName : String, password : String, repassword: String) {
        mModel.onRigisterResponse(object :LoginContract.RigisterCallback{
            override fun registerSuccess(result: LoginResponse) {
                if (result.errorCode != 0){
                    Log.e("254545665","==================")
                    mView.onRegisterFailed(result.errorMsg!!)
                }else
                    mView.onRegisterSuccess(result)
            }

            override fun registerFailed(str: String) {
                mView.onRegisterFailed(str)
            }
        },userName,password,repassword)
    }

    override fun loginResult(userName : String, password : String) {
        mModel.onLoginResponse(object : LoginContract.LoginCallback{
            override fun loginSucess(result: LoginResponse) {
                if (result.errorCode != 0){
                    mView.onLoginFailed(result.errorMsg!!)
                }else
                    mView.onLoginSucess(result)
            }

            override fun loginFailed(str: String) {
                mView.onLoginFailed(str)
            }
        },userName, password )
    }

    override fun showSuccess() {
    }

    override fun showError() {
    }
}