package com.xueyi.yang.kotlinandroid.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by YangXueYi
 * Time : 2018/1/24.
 */

/*定义一个水平的RecyclerView*/
class HorizontalRecyclerView : RecyclerView {

    /*构造函数*/
    constructor(context: Context?) : this(context,null)

    constructor(context: Context?,attrs: AttributeSet?) : this(context,attrs,0)

    constructor(context: Context?,attrs: AttributeSet?, defStyle: Int) : super(context,attrs, defStyle)

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        /*不让父类拦截滑动事件*/
        parent.requestDisallowInterceptTouchEvent(true)
        return super.dispatchTouchEvent(ev)
    }
}