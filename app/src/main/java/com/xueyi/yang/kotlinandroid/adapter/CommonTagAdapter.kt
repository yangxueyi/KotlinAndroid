package com.xueyi.yang.kotlinandroid.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.bean.FriendListResponse
import com.xueyi.yang.kotlinandroid.getRandomColor
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter

/**
 * Created by YangXueYi
 * Time : 2018/2/1.
 */
class CommonTagAdapter(val context: Context, commonTagLists :MutableList<FriendListResponse.Data>)
    : TagAdapter<FriendListResponse.Data>(commonTagLists){
    private val inflater = LayoutInflater.from(context)
    override fun getView(parent: FlowLayout?, position: Int, item: FriendListResponse.Data): View {
        return (inflater.inflate(R.layout.item_hot, parent, false) as TextView).apply {
            //设置内容
            text = item.name
            //创建一个颜色随机器
            val parseColor = try {
               Color.parseColor(getRandomColor())
            }catch (_: Exception){
                context.resources.getColor(R.color.colorAccent)
            }
            //设置字体颜色
            setTextColor(parseColor)
        }
    }

}