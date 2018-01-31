package com.xueyi.yang.kotlinandroid.fragment.hot.presenter

import com.xueyi.yang.kotlinandroid.bean.FriendListResponse
import com.xueyi.yang.kotlinandroid.bean.HotKeyResponse
import com.xueyi.yang.kotlinandroid.fragment.hot.contract.HotContract
import com.xueyi.yang.kotlinandroid.fragment.hot.model.HotModel

/**
 * Created by YangXueYi
 * Time : 2018/1/31.
 */
class HotPresenter(val hotView : HotContract.HotView, val hotModel : HotModel) : HotContract.presenter {
    override fun showSuccess() {
    }

    override fun showError() {
    }

    override fun getHotResult() {
        hotModel.onGetHotListResponse(object : HotContract.HotCallBack{
            override fun onNetworkError() {
            }

            override fun onSuccess() {
            }

            override fun onError() {
            }

            override fun getHotListSuccess(result: HotKeyResponse) {
               if (result.errorCode!=0){
                   hotView.onGetHotFailed(result.errorMsg)
                   return
               }
                if (result.data == null || result.data?.size == 0){
                    hotView.onGetHotNull()
                    return
                }
                hotView.onGetHotSuccess(result)
            }

            override fun getHotListFailed(str: String) {
                hotView.onGetHotFailed(str)
            }

        })
    }

    override fun getCommonResult() {
        hotModel.onGetCommonListResponse(object : HotContract.CommonCallBack{
            override fun onNetworkError() {
            }

            override fun onSuccess() {
            }

            override fun onError() {
            }

            override fun getCommonListSuccess(result: FriendListResponse) {
                if (result.errorCode!=0){
                    hotView.onGetCommonFailed(result.errorMsg)
                    return
                }
                if (result.data == null || result.data?.size == 0){
                    hotView.onGetCommonNull()
                    return
                }
                hotView.onGetCommonSuccess(result)
            }

            override fun getCommonListFailed(str: String) {
                hotView.onGetCommonFailed(str)
            }

        })

    }
}