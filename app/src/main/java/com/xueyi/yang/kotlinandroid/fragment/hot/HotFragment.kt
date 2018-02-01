package com.xueyi.yang.kotlinandroid.fragment.hot

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.adapter.CommonTagAdapter
import com.xueyi.yang.kotlinandroid.adapter.HotAdapter
import com.xueyi.yang.kotlinandroid.adapter.HotTagAdapter
import com.xueyi.yang.kotlinandroid.bean.FriendListResponse
import com.xueyi.yang.kotlinandroid.bean.HotKeyResponse
import com.xueyi.yang.kotlinandroid.constant.Constant
import com.xueyi.yang.kotlinandroid.fragment.hot.contract.HotContract
import com.xueyi.yang.kotlinandroid.fragment.hot.model.HotModel
import com.xueyi.yang.kotlinandroid.fragment.hot.presenter.HotPresenter
import com.xueyi.yang.kotlinandroid.module.commonContent.CommonContentActivity
import com.xueyi.yang.kotlinandroid.module.search.SearchActivity
import com.xueyi.yang.kotlinandroid.utils.ToastUtils
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * Created by YangXueYi
 * Time : 2018/1/31.
 */
class HotFragment : Fragment(), HotContract.HotView {




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

    private val hotLists = mutableListOf<FriendListResponse.Data>()

    private val hotAdapter : HotAdapter by lazy {
        HotAdapter(activity,hotLists)
    }


    private val hotTagLists = mutableListOf<HotKeyResponse.Data>()
    /*hottagadapter*/
    private val hotTagAdapter: HotTagAdapter by lazy {
        HotTagAdapter(activity, hotTagLists)
    }

    private val commonTagLists = mutableListOf<FriendListResponse.Data>()
    /*hottagadapter*/
    private val commonTagAdapter: CommonTagAdapter by lazy {
        CommonTagAdapter(activity,commonTagLists)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView ?: let {
            mainView = inflater?.inflate(R.layout.fragment_hot,container,false)
            flowLayout = inflater?.inflate(R.layout.hot_common,container,false) as LinearLayout
            tag_flow_my_bookmark = flowLayout.findViewById(R.id.tag_flow_my_bookmark)
            tag_flow_hot = flowLayout.findViewById(R.id.tag_flow_hot)
            tag_flow_common = flowLayout.findViewById(R.id.tag_flow_common)
            tv_my_bookmark = flowLayout.findViewById(R.id.tv_my_bookmark)
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

        //hot流布局的基本设置
        tag_flow_hot.run {
            adapter = hotTagAdapter
            setOnTagClickListener(onHotTagClickListener)
        }
        //common流布局的基本设置
        tag_flow_common.run {
            adapter = commonTagAdapter
            setOnTagClickListener(onCommonTagClickListener)
        }

        //hotFragment的基本设置
        recycle_view_hot.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = hotAdapter
        }
        //hotAdapter的基本设置
        hotAdapter.run {
            bindToRecyclerView(recycle_view_hot)//关联recycleview
            addHeaderView(flowLayout)//添加头布局
        }

        //获取数据
        hotPresenter.getHotResult()
        hotPresenter.getCommonResult()

    }


    override fun onShowToast(str: String) {
        ToastUtils.toast(activity,str)
    }

    override fun onShowSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onShowError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetHotSuccess(result: HotKeyResponse) {
        result.data.let {
            hotTagLists.clear()
            hotTagLists.addAll(it) //将获取的数据添加到集合
            hotTagAdapter.notifyDataChanged()//刷新
        }
        hot_swipe_refresh.isRefreshing = false
    }

    override fun onGetHotFailed(str: String) {
        str?.let{
            onShowToast(it)
        } ?: let{
            onShowToast(getString(R.string.get_data_error))
        }
        hot_swipe_refresh.isRefreshing = false
    }

    override fun onGetHotNull() {
        onShowToast(getString(R.string.get_data_zero))
        hot_swipe_refresh.isRefreshing = false
    }

    override fun onGetCommonSuccess(result: FriendListResponse) {
        result.data.let {
            commonTagLists.clear()
            commonTagLists.addAll(it)
            commonTagAdapter.notifyDataChanged()
        }
        hot_swipe_refresh.isRefreshing = false
    }

    override fun onGetCommonFailed(str: String) {
        str?.let{
            onShowToast(it)
        } ?: let{
            onShowToast(getString(R.string.get_data_error))
        }
        hot_swipe_refresh.isRefreshing = false
    }

    override fun onGetCommonNull() {
        onShowToast(getString(R.string.get_data_zero))
        hot_swipe_refresh.isRefreshing = false
    }


    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refreshData()
    }

    /*下拉刷新*/
     fun refreshData() {
        hot_swipe_refresh.isRefreshing = true
        hotPresenter.getHotResult()
        hotPresenter.getCommonResult()
    }

    private val onHotTagClickListener = TagFlowLayout.OnTagClickListener{
        _, position, _ ->
            if (hotTagLists.size != 0){
                Intent(activity,SearchActivity::class.java).run {
                    putExtra(Constant.SEARCH_KEY,true)
                    putExtra(Constant.CONTENT_TITLE_KEY, hotTagLists[position].name)
                    startActivityForResult(this,Constant.MAIN_LIKE_REQUEST_CODE)
                }
            }
        true
    }

    private val onCommonTagClickListener = TagFlowLayout.OnTagClickListener{
        _, position, _ ->

            if (commonTagLists.size != 0){
                Intent(activity,CommonContentActivity::class.java).run {
                    putExtra(Constant.CONTENT_ID_KEY,commonTagLists[position].id)
                    putExtra(Constant.CONTENT_URL_KEY,commonTagLists[position].link)
                    putExtra(Constant.CONTENT_TITLE_KEY,commonTagLists[position].name)
                    startActivity(this)
                }
            }


        true

    }

}