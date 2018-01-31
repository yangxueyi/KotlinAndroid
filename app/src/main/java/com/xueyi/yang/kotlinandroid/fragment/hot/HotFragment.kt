package com.xueyi.yang.kotlinandroid.fragment.hot

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.adapter.HotAdapter
import com.xueyi.yang.kotlinandroid.bean.FriendListResponse
import com.xueyi.yang.kotlinandroid.bean.HotKeyResponse
import com.xueyi.yang.kotlinandroid.fragment.hot.contract.HotContract
import com.xueyi.yang.kotlinandroid.fragment.hot.model.HotModel
import com.xueyi.yang.kotlinandroid.fragment.hot.presenter.HotPresenter
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * Created by YangXueYi
 * Time : 2018/1/31.
 */
class HotFragment : Fragment(), HotContract.HotView {


    private val hotLists = mutableListOf<HotKeyResponse.Data>()
    private val hotAdapter: HotAdapter by lazy {
        HotAdapter(activity, hotLists)
    }

    private var mainView : View? = null

    /*流式布局*/
    private lateinit var flowLayout: LinearLayout
    private lateinit var tag_flow_my_bookmark: TagFlowLayout
    private lateinit var tag_flow_hot: TagFlowLayout
    private lateinit var tag_flow_common: TagFlowLayout
    private lateinit var tv_my_bookmark: TextView

    /*presenter*/
    private val hotPresenter : HotPresenter by lazy {
        HotPresenter(this, HotModel())
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView ?: let {
            mainView = inflater?.inflate(R.layout.fragment_hot,container,false)
            flowLayout = inflater?.inflate(R.layout.hot_common,container,false) as LinearLayout
            tag_flow_my_bookmark = flowLayout.findViewById<TagFlowLayout>(R.id.tag_flow_my_bookmark)
            tag_flow_hot = flowLayout.findViewById<TagFlowLayout>(R.id.tag_flow_hot)
            tag_flow_common = flowLayout.findViewById<TagFlowLayout>(R.id.tag_flow_common)
            tv_my_bookmark = flowLayout.findViewById<TextView>(R.id.tv_my_bookmark)
        }
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //SwipeRefreshLayout的基本设置
        hot_swipe_refresh.run {
            isRefreshing = true
            setOnRefreshListener(onRefreshListener)
        }

        tag_flow_hot.run {
//            adapter =
        }




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


    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refreshData()
    }

    /*下拉刷新*/
    private fun refreshData() {
        hot_swipe_refresh.isRefreshing = true
        hotPresenter.getHotResult()
        hotPresenter.getCommonResult()
    }


}