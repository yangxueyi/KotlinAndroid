package com.xueyi.yang.kotlinandroid.fragment.type.presenter


import com.xueyi.yang.kotlinandroid.bean.TreeListResponse
import com.xueyi.yang.kotlinandroid.fragment.type.contract.TypeFragmentContract
import com.xueyi.yang.kotlinandroid.fragment.type.model.TypeFragmentModel

/**
 * Created by YangXueYi
 * Time : 2018/1/31.
 */
class TypeFragmentPresenter(val mTypeView : TypeFragmentContract.TypeView,val mTypeModel : TypeFragmentModel) : TypeFragmentContract.TypePresenter{
    override fun showSuccess() {
    }

    override fun showError() {
    }

    override fun getTypeListResult() {
        mTypeModel.getTypeListResponse(object :TypeFragmentContract.TypeListCallback{
            override fun onNetworkError() {
            }

            override fun onSuccess() {
            }

            override fun onError() {
            }

            override fun getTypeListSuccess(result: TreeListResponse) {

               if(result.errorCode!=0){
                   mTypeView.onTypeListFailed(result.errorMsg!!)
                   return
               }
                if(result.data.isEmpty()){
                    mTypeView.onTypeListZero()
                    return
                }
                mTypeView.onTypeListSuccess(result)
            }

            override fun getTypeListFailed(str: String) {
                mTypeView.onTypeListFailed(str)
            }
        })
    }

}