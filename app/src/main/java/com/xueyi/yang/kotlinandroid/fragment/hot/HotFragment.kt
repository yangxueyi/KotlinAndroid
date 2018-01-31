package com.xueyi.yang.kotlinandroid.fragment.hot

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xueyi.yang.kotlinandroid.bean.FriendListResponse
import com.xueyi.yang.kotlinandroid.bean.HotKeyResponse
import com.xueyi.yang.kotlinandroid.fragment.hot.contract.HotContract

/**
 * Created by YangXueYi
 * Time : 2018/1/31.
 */
class HotFragment : Fragment(), HotContract.HotView {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onShowToast(str: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onShowSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onShowError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetHotSuccess(result: HotKeyResponse) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetHotFailed(str: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetHotNull() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetCommonSuccess(result: FriendListResponse) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetCommonFailed(str: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetCommonNull() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}