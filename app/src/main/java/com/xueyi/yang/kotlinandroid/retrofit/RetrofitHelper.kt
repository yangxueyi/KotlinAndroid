package com.xueyi.yang.kotlinandroid.retrofit

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.xueyi.yang.kotlinandroid.BuildConfig
import com.xueyi.yang.kotlinandroid.constant.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by YangXueYi
 * Time : 2018/1/24.
 */
object RetrofitHelper {

    private const val TAG = "RetrofitHelper"
    private const val CONTENT_PRE = "OkHttp: "
    private const val CONNECT_TIMEOUT = 60L
    private const val READ_TIMEOUT = 10L
    private const val WRITE_TIMEOUT = 10L


    /**
     *获取 Retrofit
     */
    private fun create(url : String) : Retrofit {
        /*获取OkHttpClient*/
        val okHttpClient = OkHttpClient().newBuilder().apply {
            connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)//设置连接超时时间
            readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)//写入超时时间
            writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//读取超时时间
            addInterceptor(ResponseInterceptor)//得到响应的cookie
            addInterceptor(RequestInterceptor)//设置请求的Cookie

            if (Constant.INTERCEPTOR_ENABLE) {
                // loggingInterceptor
                addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                    Log.e(TAG,  CONTENT_PRE + it)
                }).apply {
                    // log level
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }

        return RetrofitBuild(url,
                okHttpClient.build(),
                GsonConverterFactory.create(),//gson工厂
                CoroutineCallAdapterFactory())//访问适配器工厂
                .retrofit
    }

    /**
     *获取serviceApi
     */
    open fun<T> getSevice(url: String, service: Class<T>) : T = create(url).create(service)

    /**
     * 创建 retrofit build
     */
    class RetrofitBuild(url: String, client: OkHttpClient,
                        gsonFactory: GsonConverterFactory,
                        coroutineCallAdapterFactory: CoroutineCallAdapterFactory) {
        //获取retrofit
        val retrofit : Retrofit = Retrofit.Builder().apply {
            baseUrl(url)
            client(client)
            addConverterFactory(gsonFactory)
            addCallAdapterFactory(coroutineCallAdapterFactory)
        }.build()
    }
}