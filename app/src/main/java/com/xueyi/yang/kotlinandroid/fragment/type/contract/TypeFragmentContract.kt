package com.xueyi.yang.kotlinandroid.fragment.type.contract

import com.xueyi.yang.kotlinandroid.base.contract.BaseContract
import com.xueyi.yang.kotlinandroid.base.model.BaseModel
import com.xueyi.yang.kotlinandroid.base.presenter.BasePresenter
import com.xueyi.yang.kotlinandroid.bean.HomeListResponse
import com.xueyi.yang.kotlinandroid.bean.TreeListResponse

/**
 * Created by YangXueYi
 * Time : 2018/1/31.
 */
interface TypeFragmentContract {

    interface TypeListCallback : BaseContract.BaseCallback{
        fun getTypeListSuccess(result: TreeListResponse)
        fun getTypeListFailed(str : String)
    }

    interface Model {
        fun getTypeListResponse(typeListCallback: TypeListCallback)
    }

    interface TypeView :BaseContract.BaseView{
        fun onTypeListSuccess(result: TreeListResponse)
        fun onTypeListFailed(str : String)
        fun onTypeListZero()
    }

    interface TypePresenter : BasePresenter{
        fun getTypeListResult()
    }

}