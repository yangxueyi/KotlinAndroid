package com.xueyi.yang.kotlinandroid.fragment.home.contract

import com.xueyi.yang.kotlinandroid.base.contract.BaseContract
import com.xueyi.yang.kotlinandroid.base.presenter.BasePresenter
import com.xueyi.yang.kotlinandroid.bean.BannerResponse
import com.xueyi.yang.kotlinandroid.bean.HomeListResponse

/**
 * Created by YangXueYi
 * Time : 2018/1/24.
 */
class HomeFragmentContract {


    interface BannerCallback : BaseContract.BaseCallback{
        fun getBannerSuccess(result: BannerResponse)
        fun getBannerFailed(str : String)
    }
    interface ListCallback : BaseContract.BaseCallback{
        fun getHomeListSuccess(result: HomeListResponse)
        fun getHomeListFailed(str : String)
    }

    interface CollectCallBack : BaseContract.BaseCallback{
        fun collectArticleSuccess(result: HomeListResponse, isAdd: Boolean)
        fun collectArticleFailed(str : String, isAdd: Boolean)
    }

    interface FragmentModel {
        fun onGetBannerResponse(bannerCallback: BannerCallback)
        fun onGetListResponse(listCallback: ListCallback,page: Int = 0)
        fun onCollectArticle(collectCallBack : CollectCallBack, id: Int, isAdd: Boolean)
    }

    interface FragmentView :BaseContract.BaseView{
        fun getBannerSuccess(result: BannerResponse)
        fun getBannerFailed(str : String)
        fun getListSuccess(result: HomeListResponse)
        fun getListFailed(str : String?)
        fun getListNull()
        fun getListSmall(result: HomeListResponse)
        fun collectArcitleSuccess(result: HomeListResponse,isAdd: Boolean)
        fun collectArcitleFailed(str : String?,isAdd: Boolean)
    }

    interface FragmentPresenter :BasePresenter{
        fun getBannerResult()
        fun getListResult(page: Int = 0) //提前给page赋值
        fun collectArcitle(id: Int, isAdd: Boolean)//收藏
    }
}