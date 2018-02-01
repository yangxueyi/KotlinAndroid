package com.xueyi.yang.kotlinandroid.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.bean.FriendListResponse
import com.xueyi.yang.kotlinandroid.bean.HotKeyResponse
import com.xueyi.yang.kotlinandroid.getRandomColor
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter

/**
 * Created by Administrator on 2018/1/31.
 */
class HotAdapter(val context : Context, hotLists : MutableList<FriendListResponse.Data>)
    :BaseQuickAdapter<FriendListResponse.Data,BaseViewHolder>(R.layout.item_hot,hotLists){
    override fun convert(helper: BaseViewHolder, item: FriendListResponse.Data?) {
        item ?: return
        helper.setText(R.id.tv_hot_title,item.name.trim())
    }
}


