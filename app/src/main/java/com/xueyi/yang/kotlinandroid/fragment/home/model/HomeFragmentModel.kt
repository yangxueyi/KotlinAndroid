package com.xueyi.yang.kotlinandroid.fragment.home.model

import android.util.Log
import com.xueyi.yang.kotlinandroid.bean.HomeListResponse
import com.xueyi.yang.kotlinandroid.cancelAndJoinByActive
import com.xueyi.yang.kotlinandroid.constant.Constant
import com.xueyi.yang.kotlinandroid.fragment.home.contract.HomeFragmentContract
import com.xueyi.yang.kotlinandroid.retrofit.RetrofitHelper
import com.xueyi.yang.kotlinandroid.retrofit.RetrofitService
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelAndJoin

/**
 * Created by YangXueYi
 * Time : 2018/1/24.
 */
open class HomeFragmentModel : HomeFragmentContract.FragmentModel{
    /**获取homeFragment的数据的异步：async*/
    private var homeFragmentListAsync : Deferred<Any>? = null
    /**获取banner数据的异步：async*/
    private var bannerListAsync : Deferred<Any>? = null

    override fun onGetBannerResponse(bannerCallback: HomeFragmentContract.BannerCallback) {
        async(UI) {
            try {
                bannerListAsync = RetrofitHelper.getSevice(Constant.REQUEST_BASE_URL,RetrofitService::class.java).getBannerList()
                //获取async结果
                val result = bannerListAsync?.await()


            }catch (t : Throwable){

            }
        }

    }

    override fun onGetListResponse(listCallback: HomeFragmentContract.ListCallback,page: Int) {
        /*async:异步操作
        * async { …… } 启动一个协程
        * UI:在UI线程启动一个协程
        **/
        async(UI) {
            try {
//                homeFragmentListAsync?.cancelAndJoinByActive()
                homeFragmentListAsync = RetrofitHelper.getSevice(Constant.REQUEST_BASE_URL, RetrofitService::class.java).getHomeList(page)
                //获取async结果
                val result = homeFragmentListAsync?.await()
                if (result.toString().isNotEmpty()) {//访问成功,判断数据是否获取成功
                    listCallback.onSuccess()
                    if (result is HomeListResponse) {//获取数据成功
                        listCallback.getHomeListSuccess(result)
                    } else {//获取数据失败
                        listCallback.getHomeListFailed(Constant.RESULT_NULL)
                    }
                } else {//访问失败
                    listCallback.onError()
                }
            }catch (t: Throwable){
                t.printStackTrace()
                Log.e("HomeFragmentModel","t.toString() = $t.toString()")
                listCallback.getHomeListFailed(t.toString())
                return@async
            }

        }

    }
}