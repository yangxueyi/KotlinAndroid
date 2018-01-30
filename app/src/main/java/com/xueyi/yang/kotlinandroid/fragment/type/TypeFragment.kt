package com.xueyi.yang.kotlinandroid.fragment.type

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xueyi.yang.kotlinandroid.R

/**
 * Created by YangXueYi
 * Time : 2018/1/23.
 */
class TypeFragment : Fragment() {
    /*mainView*/
    private var mainView : View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /*
           * 如果HomeFragment的添加数据失败，那么就不显示banner轮播图
           * 所以添加了一个let函数
           * let:不为空的时候才执行lambda
           */
        mainView ?:let {
            mainView = inflater.inflate(R.layout.fragment_type,container,false)
        }
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}