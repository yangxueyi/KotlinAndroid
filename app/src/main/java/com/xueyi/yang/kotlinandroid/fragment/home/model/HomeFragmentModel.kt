package com.xueyi.yang.kotlinandroid.fragment.home.model

import android.util.Log
import com.xueyi.yang.kotlinandroid.bean.BannerResponse
import com.xueyi.yang.kotlinandroid.bean.HomeListResponse
import com.xueyi.yang.kotlinandroid.cancelAndJoinByActive
import com.xueyi.yang.kotlinandroid.constant.Constant
import com.xueyi.yang.kotlinandroid.fragment.home.contract.HomeFragmentContract
import com.xueyi.yang.kotlinandroid.retrofit.RetrofitHelper
import com.xueyi.yang.kotlinandroid.retrofit.RetrofitService
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.android.UI
import java.security.KeyStore

/**
 * Created by YangXueYi
 * Time : 2018/1/24.
 */
open class HomeFragmentModel : HomeFragmentContract.FragmentModel{

    /**获取homeFragment的数据的异步：async*/
    private var homeFragmentListAsync : Deferred<Any>? = null
    /**获取banner数据的异步：async*/
    private var bannerListAsync : Deferred<Any>? = null

    /**获取收藏数据的异步：async*/
    private var collectArticleAsync : Deferred<Any>? = null

    /*banner数据获取*/
    override fun onGetBannerResponse(bannerCallback: HomeFragmentContract.BannerCallback) {
        /*async:异步操作
       * async { …… } 创建一个协程
       * UI:在UI线程启动一个协程
       **/
        async(UI) {
            try {
                bannerListAsync?.cancelAndJoinByActive()
                bannerListAsync = RetrofitHelper.getSevice(Constant.REQUEST_BASE_URL,RetrofitService::class.java).getBannerList()
                //获取async结果
                val result = bannerListAsync?.await()
                if(result is BannerResponse){
                    bannerCallback.getBannerSuccess(result)
                }else{
                    bannerCallback.getBannerFailed(Constant.RESULT_NULL)
                }
            }catch (t : Throwable){
                t.printStackTrace()
                bannerCallback.getBannerFailed(t.toString())
                return@async
            }
        }

    }

    /*homeFragment数据获取*/
    override fun onGetListResponse(listCallback: HomeFragmentContract.ListCallback,page: Int) {
        /*async:异步操作
        * async { …… } 创建一个协程
        * UI:在UI线程启动一个协程
        **/
        async(UI) {
            try {
                homeFragmentListAsync?.cancelAndJoinByActive()
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
//                Log.e("HomeFragmentModel","t.toString() = $t.toString()")
                listCallback.getHomeListFailed(t.toString())
                return@async
            }
        }
    }

    /*收藏列表*/
    override fun onCollectArticle(collectCallBack: HomeFragmentContract.CollectCallBack, id: Int, isAdd: Boolean) {
        async(UI){
            try {
                collectArticleAsync.cancelAndJoinByActive()
                collectArticleAsync = if (isAdd) {
                    RetrofitHelper.getSevice(Constant.REQUEST_BASE_URL,RetrofitService::class.java).addCollectArticle(id)
                } else {
                    RetrofitHelper.getSevice(Constant.REQUEST_BASE_URL,RetrofitService::class.java).removeCollectArticle(id)
                }
                //获取async结果
                val result = collectArticleAsync?.await()
                if (result is HomeListResponse){
                    collectCallBack.collectArticleSuccess(result,isAdd)
                }else{
                    collectCallBack.collectArticleFailed(Constant.RESULT_NULL,isAdd)
                }
            }catch (t : Throwable){
                collectCallBack.collectArticleFailed(t.toString(),isAdd)
                return@async
            }
        }
    }
}