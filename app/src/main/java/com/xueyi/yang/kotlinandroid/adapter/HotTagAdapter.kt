package com.xueyi.yang.kotlinandroid.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.bean.HotKeyResponse
import com.xueyi.yang.kotlinandroid.getRandomColor
import com.xueyi.yang.kotlinandroid.module.welcome.contract.WelcomeContract
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import java.util.*

/**
 * Created by Administrator on 2018/1/31.
 */
class HotTagAdapter(val context : Context, hotTagLists : MutableList<HotKeyResponse.Data>)
    : TagAdapter<HotKeyResponse.Data>(hotTagLists) { //继承flowlayout的adapter
    //创建一个inflater
    private val inflater : LayoutInflater = LayoutInflater.from(context)
    override fun getView(parent: FlowLayout?, position: Int, data: HotKeyResponse.Data): View {
        return (inflater.inflate(R.layout.item_hot_tag, parent, false) as TextView).apply {
            //设置内容
            text = data.name

            //创建一个颜色随机器
            var parseColor = try {
               Color.parseColor(getRandomColor())
            }catch (_: Exception){
                context.resources.getColor(R.color.colorAccent)
            }

            //设置内容颜色
            setTextColor(parseColor)
        }
    }
}