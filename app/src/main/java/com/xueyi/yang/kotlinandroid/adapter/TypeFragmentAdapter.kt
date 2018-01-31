package com.xueyi.yang.kotlinandroid.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.bean.TreeListResponse

/**
 * Created by YangXueYi
 * Time : 2018/1/25.
 */
class TypeFragmentAdapter(val context : Context, typeLists: MutableList<TreeListResponse.Data>):
        BaseQuickAdapter<TreeListResponse.Data, BaseViewHolder>(R.layout.item_fragment_type,typeLists){
    override fun convert(helper: BaseViewHolder, item: TreeListResponse.Data?) {
        item?:return
        helper.setText(R.id.tv_type_item_title,item.name)
        item.children?.let {
            children ->  helper.setText(R.id.tv_type_item_title_2,children.joinToString("     ", transform = {
            child -> child.name
        }))
        }
    }

}