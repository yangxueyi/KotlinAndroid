package com.xueyi.yang.kotlinandroid.adapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.bean.BannerResponse

/**
 * Created by YangXueYi
 * Time : 2018/1/25.
 */
class BannerAdapter(val context : Context, bannerList: MutableList<BannerResponse.Data>):
        BaseQuickAdapter<BannerResponse.Data,BaseViewHolder> (R.layout.item_banner,bannerList){

    /*override fun getItemCount(): Int {
        return if (bannerList == null) 0 else if (bannerList.size < 2) bannerList.size else Integer.MAX_VALUE
    }*/

    override fun convert(helper: BaseViewHolder, item: BannerResponse.Data?) {
        item?:return
        helper.setText(R.id.bannerTitle,item.title.trim())

        val bannerImage = helper.getView<ImageView>(R.id.bannerImage)//获取图片对象

        Glide.with(context).load(item.imagePath)/*.apply(options)*/.into(bannerImage)


    }
}