package com.xueyi.yang.kotlinandroid.fragment.hot.model

import com.xueyi.yang.kotlinandroid.bean.FriendListResponse
import com.xueyi.yang.kotlinandroid.bean.HotKeyResponse
import com.xueyi.yang.kotlinandroid.cancelAndJoinByActive
import com.xueyi.yang.kotlinandroid.constant.Constant
import com.xueyi.yang.kotlinandroid.fragment.hot.contract.HotContract
import com.xueyi.yang.kotlinandroid.retrofit.RetrofitHelper
import com.xueyi.yang.kotlinandroid.retrofit.RetrofitService
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

/**
 * Created by YangXueYi
 * Time : 2018/1/31.
 */
class HotModel : HotContract.Model {
    /**获取hotFragment大家都在收的数据的异步：async*/
    private var hotListAsync : Deferred<HotKeyResponse>? = null
    /**获取hotFragment的常用网站的数据的异步：async*/
    private var commonListAsync : Deferred<FriendListResponse>? = null
    override fun onGetHotListResponse(hotCallBack: HotContract.HotCallBack) {
        async(UI) {
            try {
                hotListAsync.cancelAndJoinByActive()
                hotListAsync = RetrofitHelper.getSevice(Constant.REQUEST_BASE_URL,RetrofitService::class.java).getHotList()
                val result = hotListAsync?.await()
                if(result is HotKeyResponse){
                    hotCallBack.getHotListSuccess(result)
                }else{
                    hotCallBack.getHotListFailed(Constant.RESULT_NULL)
                }
            }catch (t : Throwable){
                hotCallBack.getHotListFailed(t.toString())
                return@async
            }
        }
    }

    override fun onGetCommonListResponse(commonCallBack: HotContract.CommonCallBack) {
        async(UI) {
            try {
                commonListAsync.cancelAndJoinByActive()
                commonListAsync = RetrofitHelper.getSevice(Constant.REQUEST_BASE_URL,RetrofitService::class.java).getFriendList()
                val result = commonListAsync?.await()
                if(result is FriendListResponse){
                    commonCallBack.getCommonListSuccess(result)
                }else{
                    commonCallBack.getCommonListFailed(Constant.RESULT_NULL)
                }
            }catch (t : Throwable){
                commonCallBack.getCommonListFailed(t.toString())
                return@async
            }
        }
    }
}