package com.xueyi.yang.kotlinandroid.fragment.type

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.adapter.TypeFragmentAdapter
import com.xueyi.yang.kotlinandroid.bean.TreeListResponse
import com.xueyi.yang.kotlinandroid.constant.Constant
import com.xueyi.yang.kotlinandroid.fragment.type.contract.TypeFragmentContract
import com.xueyi.yang.kotlinandroid.fragment.type.model.TypeFragmentModel
import com.xueyi.yang.kotlinandroid.fragment.type.presenter.TypeFragmentPresenter
import com.xueyi.yang.kotlinandroid.module.typeContract.TypeContentActivity
import com.xueyi.yang.kotlinandroid.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_type.*

/**
 * Created by YangXueYi
 * Time : 2018/1/23.
 */
class TypeFragment : Fragment(), TypeFragmentContract.TypeView{

    /*mainView*/
    private var mainView : View? = null

    private var typeLists = mutableListOf<TreeListResponse.Data>()

    /*获取presenter*/
    private val typePresenter : TypeFragmentPresenter by lazy {
        TypeFragmentPresenter(this, TypeFragmentModel())
    }
    /*获取adapter*/
    private val typeAdapter : TypeFragmentAdapter by lazy {
        TypeFragmentAdapter(activity,typeLists)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView?:let {
            mainView = inflater.inflate(R.layout.fragment_type,container,false)
        }
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //SwipeRefreshLayout的基本设置
        type_SwipeRefreshLayout.run {
            isRefreshing = true
            setOnRefreshListener(myOnRefreshListener)
        }
        //RecyclerView的基本设置
        type_RecyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = typeAdapter
        }
        //typeAdapter的基本设置
        typeAdapter.run{
            bindToRecyclerView(type_RecyclerView)//和type_RecyclerView关联
            setEmptyView(R.layout.fragment_empty)
            onItemClickListener = this@TypeFragment.onItemClickListener

        }
        typePresenter.getTypeListResult()
    }

    override fun onShowToast(str: String) {
        ToastUtils.toast(activity,str)
    }

    override fun onShowSuccess() {
    }

    override fun onShowError() {
    }

    override fun onTypeListSuccess(result: TreeListResponse) {
        result.data.let {
            if (type_SwipeRefreshLayout.isRefreshing){
                typeAdapter.replaceData(it)
            }else{
                typeAdapter.addData(it)
            }
        }
        type_SwipeRefreshLayout.isRefreshing = false
    }

    override fun onTypeListFailed(str: String) {
        onShowToast(str)
        type_SwipeRefreshLayout.isRefreshing = false
    }
    override fun onTypeListZero() {
        onShowToast(getString(R.string.get_data_zero))
        type_SwipeRefreshLayout.isRefreshing = false
    }



    /**
     * scroll to top
     */
    fun smoothScrollToPosition() = type_RecyclerView.scrollToPosition(0)


    private val myOnRefreshListener = SwipeRefreshLayout.OnRefreshListener{
        refreshData()
    }
    /*加载更多*/
    private fun refreshData() {
        type_SwipeRefreshLayout.isRefreshing = true
        typePresenter.getTypeListResult()
    }

    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener{
        _, _, position ->
            if (typeLists.size!=0){
                Intent(activity,TypeContentActivity::class.java).apply {
                    putExtra(Constant.CONTENT_TITLE_KEY,typeLists[position].name)
                    putExtra(Constant.CONTENT_CHILDREN_DATA_KEY,typeLists[position])
                    startActivity(this)

                }
            }
    }


}