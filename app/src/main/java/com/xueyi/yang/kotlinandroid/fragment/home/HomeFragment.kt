package com.xueyi.yang.kotlinandroid.fragment.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.adapter.BannerAdapter
import com.xueyi.yang.kotlinandroid.adapter.HomeFragmentAdapter
import com.xueyi.yang.kotlinandroid.bean.BannerResponse
import com.xueyi.yang.kotlinandroid.bean.HomeDatas
import com.xueyi.yang.kotlinandroid.bean.HomeListResponse
import com.xueyi.yang.kotlinandroid.fragment.home.contract.HomeFragmentContract
import com.xueyi.yang.kotlinandroid.fragment.home.model.HomeFragmentModel
import com.xueyi.yang.kotlinandroid.fragment.home.presenter.HomeFragmentPresenter
import com.xueyi.yang.kotlinandroid.utils.ToastUtils
import com.xueyi.yang.kotlinandroid.view.HorizontalRecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_banner.*

/**
 * Created by YangXueYi
 * Time : 2018/1/23.
 */
class HomeFragment : Fragment(),HomeFragmentContract.FragmentView {

    /*mainView*/
    private var mainView : View? = null
    /*banner布局，使用水平recyclerview*/
    private lateinit var bannerRecyclerView :HorizontalRecyclerView

    //homeadapter的数据
    private val lists = mutableListOf<HomeDatas>()
    //banner的数据
    private val bannerList = mutableListOf<BannerResponse.Data>()
    /*homeFragment的适配器*/
    private val homeAdapter : HomeFragmentAdapter by lazy {
        HomeFragmentAdapter(activity,lists)
    }
    /*banner的适配器*/
    private val bannerAdapter :BannerAdapter by lazy {
        BannerAdapter(activity,bannerList)
    }

    /*获取presenter*/
    private val homeFragmentPresenter : HomeFragmentPresenter by lazy {
         HomeFragmentPresenter(this,HomeFragmentModel())
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /*
        * 如果HomeFragment的添加数据失败，那么就不显示banner轮播图
        * 所以添加了一个let函数
        * let:不为空的时候才执行lambda
        */
        mainView?:let {
            mainView = inflater?.inflate(R.layout.fragment_home,container,false)
            bannerRecyclerView = inflater?.inflate(R.layout.fragment_home_banner,null) as HorizontalRecyclerView
        }
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //SwipeRefreshLayout的基本设置
        swipe_refresh.run {
            isRefreshing = true
            setOnRefreshListener(OnRefreshListener)
        }
        //recycle_view基本设置
        recycle_view_home.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = homeAdapter
        }
        //banner的基本操作
        banner_home.run {

        }

        //homeadapter的基本操作
        homeAdapter.run {

        }
        //拿到recycle_view内容
        homeFragmentPresenter.getListResult()
    }


    override fun getBannerSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBannerFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBannerNull() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getListSuccess(result: HomeListResponse) {
        result.data.datas?.let {
            homeAdapter.run {
                //列表总数
                val total = result.data.total
                //当前总数 >  列表总数
                if(result.data.offset >= total || data.size >= total){
                    loadMoreEnd() //结束，不在有数据
                    return@let
                }
                //判断是第一次加载数据，还是刷新
                if(swipe_refresh.isRefreshing){
                    replaceData(it)
                }else{
                    addData(it)
                }

                loadMoreComplete()//加载完成
                setEnableLoadMore(true)//设置启用的负载状态
            }
        }
        swipe_refresh.isRefreshing = false
    }

    override fun getListFailed(str : String) {
        onShowToast(str)
    }

    override fun getListNull() {

    }

    override fun getListSmall(result: HomeListResponse) {
        result.data.datas?.let {
            homeAdapter.run {
                replaceData(it)
                loadMoreComplete()
                loadMoreEnd()
                setEnableLoadMore(false)
            }
        }
        swipe_refresh.isRefreshing = false
    }

    override fun onShowToast(str: String) {
        ToastUtils.toast(activity,str)
    }

    override fun onShowSuccess() {
        onShowToast("获取数据成功")
    }

    override fun onShowError() {
        onShowToast("获取数据失败")
    }

    private val OnRefreshListener = SwipeRefreshLayout.OnRefreshListener{
        refreshData()
    }

    /**
     *滚动到顶部
     */
    fun smoothScrollToPosition() = recycle_view_home.scrollToPosition(0)

    /*刷新数据*/
     fun refreshData() {
        swipe_refresh.isRefreshing = true
        homeAdapter.setEnableLoadMore(false)
//        cancelSwitchJob()
        homeFragmentPresenter.getListResult()
    }
}