package com.xueyi.yang.kotlinandroid.retrofit

import com.xueyi.yang.kotlinandroid.utils.CookieUtils.Companion.encodeCookie
import com.xueyi.yang.kotlinandroid.utils.CookieUtils.Companion.saveCookie
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by YangXueYi
 * Time : 2018/1/24.
 */

/*响应header文件*/
object ResponseInterceptor : Interceptor {

    private const val SAVE_USER_LOGIN_KEY = "user/login"
    private const val SAVE_USER_REGISTER_KEY = "user/register"
    private const val SET_COOKIE_KEY = "set-cookie"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val requestUrl = request.url().toString()
        val domain = request.url().host()
        // set-cookie maybe has multi, login to save cookie
        if ((requestUrl.contains(SAVE_USER_LOGIN_KEY) || requestUrl.contains(SAVE_USER_REGISTER_KEY))
                && !response.headers(SET_COOKIE_KEY).isEmpty()) {
            val cookies = response.headers(SET_COOKIE_KEY)
            val cookie = encodeCookie(cookies)
            saveCookie(requestUrl,domain,cookie)
        }
        return response
    }
}