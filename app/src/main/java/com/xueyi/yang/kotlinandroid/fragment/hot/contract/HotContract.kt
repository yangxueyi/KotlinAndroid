package com.xueyi.yang.kotlinandroid.fragment.hot.contract

import com.xueyi.yang.kotlinandroid.base.contract.BaseContract
import com.xueyi.yang.kotlinandroid.base.presenter.BasePresenter
import com.xueyi.yang.kotlinandroid.bean.FriendListResponse
import com.xueyi.yang.kotlinandroid.bean.HotKeyResponse

/**
 * Created by YangXueYi
 * Time : 2018/1/31.
 */
class HotContract {

    interface HotCallBack : BaseContract.BaseCallback{
        fun getHotListSuccess(result: HotKeyResponse)
        fun getHotListFailed(str: String)
    }
    interface CommonCallBack : BaseContract.BaseCallback{
        fun getCommonListSuccess(result: FriendListResponse)
        fun getCommonListFailed(str: String)
    }

    interface Model {
        fun onGetHotListResponse(hotCallBack: HotCallBack) //获取热门网站
        fun onGetCommonListResponse(commonCallBack: CommonCallBack)//获取常用网站
    }


    interface HotView :BaseContract.BaseView{
        fun onGetHotSuccess(result: HotKeyResponse)
        fun onGetHotFailed(str: String)
        fun onGetHotNull()

        fun onGetCommonSuccess(result: FriendListResponse)
        fun onGetCommonFailed(str: String)
        fun onGetCommonNull()
    }

    interface presenter : BasePresenter{
        fun getHotResult()
        fun getCommonResult()
    }


}