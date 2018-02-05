package com.xueyi.yang.kotlinandroid.module.login.model

import com.xueyi.yang.kotlinandroid.bean.LoginResponse
import com.xueyi.yang.kotlinandroid.cancelAndJoinByActive
import com.xueyi.yang.kotlinandroid.constant.Constant
import com.xueyi.yang.kotlinandroid.module.login.contract.LoginContract
import com.xueyi.yang.kotlinandroid.retrofit.RetrofitHelper
import com.xueyi.yang.kotlinandroid.retrofit.RetrofitService
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

/**
 * Created by YangXueYi
 * Time : 2018/2/5.
 */
class LoginModel : LoginContract.mModel {

    /**获取loginActivity注册的异步数据：async*/
    private var rigisterListAsync : Deferred<Any>? = null
    /**获取loginActivity登录的异步数据：async*/
    private var loginListAsync : Deferred<Any>? = null

    override fun onRigisterResponse(rigisterCallback: LoginContract.RigisterCallback,
                                    userName: String, password: String, repassword: String) {
        async(UI) {
           try {
               rigisterListAsync?.cancelAndJoinByActive()
               rigisterListAsync = RetrofitHelper.getSevice(Constant.REQUEST_BASE_URL,RetrofitService::class.java)
                       .getRegisterList(userName,password,repassword)
               //拿到结果
               val result = rigisterListAsync?.await()
               if(result is LoginResponse){
                   rigisterCallback.registerSuccess(result)
               }else{
                   rigisterCallback.registerFailed(Constant.RESULT_NULL)
               }
           }catch (t : Throwable){
               println("t ======================== $t")
               rigisterCallback.registerFailed(t.toString())
               return@async
           }
        }
    }

    override fun onLoginResponse(loginCallback: LoginContract.LoginCallback,
                                 userName : String, password : String) {
        async(UI) {
            try {
                loginListAsync?.cancelAndJoinByActive()
                loginListAsync = RetrofitHelper.getSevice(Constant.REQUEST_BASE_URL,RetrofitService::class.java).
                        getLoginList(userName,password)
                //拿到结果
                val result = loginListAsync?.await()
                if(result is LoginResponse){
                    loginCallback.loginSucess(result)
                }else{
                    loginCallback.loginFailed(Constant.RESULT_NULL)
                }
            }catch (t : Throwable){
                loginCallback.loginFailed(t.toString())
                return@async
            }
        }
    }
}