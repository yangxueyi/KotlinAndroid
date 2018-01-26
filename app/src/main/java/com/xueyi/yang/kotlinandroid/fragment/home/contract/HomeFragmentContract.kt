package com.xueyi.yang.kotlinandroid.fragment.home.contract

import com.xueyi.yang.kotlinandroid.base.contract.BaseContract
import com.xueyi.yang.kotlinandroid.base.presenter.BasePresenter
import com.xueyi.yang.kotlinandroid.bean.HomeListResponse

/**
 * Created by YangXueYi
 * Time : 2018/1/24.
 */
class HomeFragmentContract {


    interface BannerCallback : BaseContract.BaseCallback
    interface ListCallback : BaseContract.BaseCallback{
        fun getHomeListSuccess(result: HomeListResponse)
        fun getHomeListFailed(str : String)
    }

    interface FragmentModel {
        fun onGetBannerResponse(bannerCallback: BannerCallback)
        fun onGetListResponse(listCallback: ListCallback,page: Int = 0)
    }

    interface FragmentView :BaseContract.BaseView{
        fun getBannerSuccess()
        fun getBannerFailed()
        fun getBannerNull()
        fun getListSuccess(result: HomeListResponse)
        fun getListFailed(str : String)
        fun getListNull()
        fun getListSmall(result: HomeListResponse)
    }

    interface FragmentPresenter :BasePresenter{
        fun getBannerResult()
        fun getListResult(page: Int = 0) //提前给page赋值
    }
}