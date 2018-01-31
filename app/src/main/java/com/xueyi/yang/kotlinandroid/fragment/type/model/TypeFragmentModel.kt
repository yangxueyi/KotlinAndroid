package com.xueyi.yang.kotlinandroid.fragment.type.model

import com.xueyi.yang.kotlinandroid.bean.TreeListResponse
import com.xueyi.yang.kotlinandroid.cancelAndJoinByActive
import com.xueyi.yang.kotlinandroid.constant.Constant
import com.xueyi.yang.kotlinandroid.fragment.type.contract.TypeFragmentContract
import com.xueyi.yang.kotlinandroid.retrofit.RetrofitHelper
import com.xueyi.yang.kotlinandroid.retrofit.RetrofitService
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

/**
 * Created by YangXueYi
 * Time : 2018/1/31.
 */
class TypeFragmentModel : TypeFragmentContract.Model{

    /**获取typeFragment的数据的异步：async*/
    private var typeListAsync : Deferred<Any>? = null


    override fun getTypeListResponse(typeListCallback: TypeFragmentContract.TypeListCallback) {

        async(UI) {
            try {
                typeListAsync?.cancelAndJoinByActive()
                typeListAsync = RetrofitHelper.getSevice(Constant.REQUEST_BASE_URL,RetrofitService::class.java).getTypeTreeList()
                val result = typeListAsync?.await()
                if(result is TreeListResponse){
                    typeListCallback.getTypeListSuccess(result)
                }else{
                    typeListCallback.getTypeListFailed(Constant.RESULT_NULL)
                }

            }catch (t : Throwable){
                t.printStackTrace()
                typeListCallback.getTypeListFailed(t.toString())
                return@async
            }
        }
    }
}