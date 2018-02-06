package com.xueyi.yang.kotlinandroid.retrofit

import com.xueyi.yang.kotlinandroid.utils.SpUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by YangXueYi
 * Time : 2018/1/24.
 */

/*请求header文件*/
object RequestInterceptor : Interceptor {

    private const val COOKIE_NAME = "Cookie"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val domain = request.url().host()
        // get domain cookie
        if (domain.isNotEmpty()) {
            val spDomain: String by SpUtils(domain, "")
            val cookie: String = if (spDomain.isNotEmpty()) spDomain else ""
            if (cookie.isNotEmpty()) {
                builder.addHeader(COOKIE_NAME, cookie)
            }
        }
        return chain.proceed(builder.build())
    }
}