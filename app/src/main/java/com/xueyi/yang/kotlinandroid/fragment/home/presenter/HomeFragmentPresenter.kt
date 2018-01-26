package com.xueyi.yang.kotlinandroid.fragment.home.presenter

import com.xueyi.yang.kotlinandroid.bean.HomeListResponse
import com.xueyi.yang.kotlinandroid.fragment.home.contract.HomeFragmentContract
import com.xueyi.yang.kotlinandroid.fragment.home.model.HomeFragmentModel

/**
 * Created by YangXueYi
 * Time : 2018/1/24.
 */
class HomeFragmentPresenter(val homeFragmentView : HomeFragmentContract.FragmentView,val homeFragmentModel: HomeFragmentModel) :HomeFragmentContract.FragmentPresenter{
    override fun showSuccess() {
    }

    override fun showError() {
    }

    override fun getBannerResult() {
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
}