package com.xueyi.yang.kotlinandroid.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

/**
 * Created by YangXueYi
 * Time : 2018/1/19.
 */
class GuideViewpageAdapter(private val list : ArrayList<View>) :PagerAdapter() {


    /*删除条目*/
    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(list[position])
    }
    /*实例化条目*/
    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
         container?.addView(list[position],0)
        return list[position]
    }
    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`
    }
    override fun getCount(): Int {
        return list?.size
    }
}