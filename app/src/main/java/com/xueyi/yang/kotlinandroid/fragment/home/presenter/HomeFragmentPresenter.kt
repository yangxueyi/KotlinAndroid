package com.xueyi.yang.kotlinandroid.fragment.home.presenter

import com.xueyi.yang.kotlinandroid.bean.BannerResponse
import com.xueyi.yang.kotlinandroid.bean.HomeListResponse
import com.xueyi.yang.kotlinandroid.fragment.home.contract.HomeFragmentContract
import com.xueyi.yang.kotlinandroid.fragment.home.model.HomeFragmentModel

/**
 * Created by YangXueYi
 * Time : 2018/1/24.
 */
class HomeFragmentPresenter(val homeFragmentView : HomeFragmentContract.FragmentView, private val homeFragmentModel: HomeFragmentModel) :HomeFragmentContract.FragmentPresenter{


    override fun showSuccess() {
    }

    override fun showError() {
    }

    override fun getBannerResult() {

        homeFragmentModel.onGetBannerResponse(object : HomeFragmentContract.BannerCallback{
            override fun getBannerSuccess(result: BannerResponse) {
                homeFragmentView.getBannerSuccess(result)
            }

            override fun getBannerFailed(str: String) {
                homeFragmentView.getBannerFailed(str)
            }

            override fun onNetworkError() {}
            override fun onSuccess() {}
            override fun onError() {}
        })
    }

    override fun getListResult(page: Int) {
        homeFragmentModel.onGetListResponse(object : HomeFragmentContract.ListCallback{
            override fun getHomeListSuccess(result: HomeListResponse) {
                if(result.errorCode !=0){
                    homeFragmentView.getListFailed(result.errorMsg)
                    return
                }
                // 列表总数
                val total = result.data.total
                if(total == 0){//没数据
                    homeFragmentView.getListNull()
                    return
                }
                // 当第一页小于一页总数时
                if(total < result.data.size){
                    homeFragmentView.getListSmall(result)
                    return
                }
                homeFragmentView.getListSuccess(result)
            }

            override fun getHomeListFailed(string: String) {
                homeFragmentView.getListFailed(string)
            }

            override fun onNetworkError() {
            }

            override fun onSuccess() {
                homeFragmentView.onShowSuccess()
            }

            override fun onError() {
                homeFragmentView.onShowError()
            }

        },page)
    }

    override fun collectArcitle(id: Int, isAdd: Boolean) {
        homeFragmentModel.onCollectArticle(object : HomeFragmentContract.CollectCallBack{
            override fun onNetworkError() {
            }

            override fun onSuccess() {
            }

            override fun onError() {
            }

            override fun collectArticleFailed(str: String,isAdd: Boolean) {
                homeFragmentView.collectArcitleFailed(str,isAdd)
            }

            override fun collectArticleSuccess(result: HomeListResponse,isAdd: Boolean) {
                if (result.errorCode!=0){
                    homeFragmentView.collectArcitleFailed(result.errorMsg, isAdd)
                }else{
                    homeFragmentView.collectArcitleSuccess(result,isAdd)
                }
            }


        },id,isAdd)
    }
}