package com.xueyi.yang.kotlinandroid.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.bean.HomeDatas

/**
 * Created by YangXueYi
 * Time : 2018/1/24.
 */
class HomeFragmentAdapter(val context: Context,list : MutableList<HomeDatas>)
    :BaseQuickAdapter<HomeDatas,BaseViewHolder>(R.layout.item_fragment_home,list){
    override fun convert(helper: BaseViewHolder, item: HomeDatas?) {
        item?:return //为空就退出
        helper.setText(R.id.tv_home_item_title,item.title)
                .setText(R.id.tv_home_item_writerName,item.author)
                .setText(R.id.tv_home_item_type,item.chapterName)
                .setText(R.id.tv_home_item_data, item.niceDate)
                .addOnClickListener(R.id.tv_home_item_type)//指定某个控件可点击
                .setTextColor(R.id.tv_home_item_type,context.resources.getColor(R.color.colorPrimary_1))//颜色
                .linkify(R.id.tv_home_item_type)//链接
                //判断是否收藏
                .setImageResource(R.id.iv_home_item_like,if (item.collect) R.drawable.ic_action_my_like else R.drawable.ic_action_no_like)
                .addOnClickListener(R.id.iv_home_item_like)

    }
}