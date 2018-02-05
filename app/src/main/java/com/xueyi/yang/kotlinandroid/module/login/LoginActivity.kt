package com.xueyi.yang.kotlinandroid.module.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.base.BaseActivity
import com.xueyi.yang.kotlinandroid.bean.LoginResponse
import com.xueyi.yang.kotlinandroid.constant.Constant
import com.xueyi.yang.kotlinandroid.module.login.contract.LoginContract
import com.xueyi.yang.kotlinandroid.module.login.model.LoginModel
import com.xueyi.yang.kotlinandroid.module.login.presenter.LoginPresenter
import com.xueyi.yang.kotlinandroid.utils.SpUtils
import com.xueyi.yang.kotlinandroid.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by YangXueYi
 * Time : 2018/1/23.
 */
class LoginActivity :BaseActivity(), LoginContract.LoginView{


    /*拿到是否已经登录*/
    private var isLogin : Boolean by SpUtils(Constant.LOGIN_KEY,false)
    /*拿到用户名*/
    private var user : String by SpUtils(Constant.USERNAME_KEY,"")
    /*拿到密码*/
    private var psw : String by SpUtils(Constant.PASSWORD_KEY,"")

    private val loginPresenter : LoginPresenter by lazy {
        LoginPresenter(this,LoginModel())
    }

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

    override fun onRegisterSuccess(result : LoginResponse) {
        onShowToast(getString(R.string.register_success))
    }

    override fun onRegisterFailed(str: String) {
        isLogin = false
        et_username.requestFocus()
        str ?.let {
            onShowToast(it)
        }
    }

    override fun onLoginSucess(result : LoginResponse) {
        onShowToast(getString(R.string.login_success))
        loginAfter(result)
    }


    override fun onLoginFailed(str: String) {
        isLogin = false
        str ?.let {
            onShowToast(it)
        }
    }

    override fun onShowToast(str: String) {
        ToastUtils.toast(this,str)
    }

    /*登录成功后的操作*/
    private fun loginAfter(result: LoginResponse) {
        //调用sp的setValue
        isLogin = true
        user = result.data.username
        psw = result.data.password
        //返回的信息
        setResult(Activity.RESULT_OK, Intent().apply { putExtra(Constant.CONTENT_TITLE_KEY,result.data.username) })
        finish()
    }

    private val onClickListener = View.OnClickListener{
        view ->
        when(view.id){
            R.id.btn_register -> {
                if (checkContent()){
                    loginPresenter.registerResult(et_username.text.toString()
                            ,et_password.text.toString(),et_password.text.toString())
                }
            }
            R.id.btn_login -> {
                loginPresenter.loginResult(et_username.text.toString(),et_password.text.toString())
            }
            R.id.iv_login_exit -> {
                finish()
            }
        }
    }

    //判断用户名和密码
    private fun checkContent(): Boolean {
        //获取edittext中的内容
        // error hint
        et_username.error = null
        et_password.error = null
        // cancel
        var cancel = false
        // attempt to view
        var focusView: View? = null
        // if register, check password confirm
        val pwdText = et_password.text.toString()
        val usernameText = et_username.text.toString()

        // check password
        if (TextUtils.isEmpty(pwdText)) {
            et_password.error = getString(R.string.password_not_empty)
            focusView = et_password
            cancel = true
        } else if (et_password.text.length < 6) {
            et_password.error = getString(R.string.password_length_short)
            focusView = et_password
            cancel = true
        }

        // check username
        if (TextUtils.isEmpty(usernameText)) {
            et_username.error = getString(R.string.username_not_empty)
            focusView = et_username
            cancel = true
        }

        // requestFocus
        if (cancel) {
            if (focusView != null) {
                focusView.requestFocus()
            }
            return false
        } else {
            return true
        }
    }
}