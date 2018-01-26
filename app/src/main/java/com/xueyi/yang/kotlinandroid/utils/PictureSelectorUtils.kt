package com.xueyi.yang.kotlinandroid.utils

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia

/**
 * Created by YangXueYi
 * Time : 2018/1/23.
 */
class PictureSelectorUtils {

    companion object {
        fun select(context: AppCompatActivity,list :List<LocalMedia>){
            //图片的选择
            PictureSelector.create(context)
                    .openGallery(PictureMimeType.ofAll())//进入相册
                    .selectionMode(PictureConfig.SINGLE)//单选 or 多选
                    .enableCrop(true)//是否剪裁
                    .sizeMultiplier(0.5f)//加载图片大小：0--1之间
                    .compress(true)//是否压缩
                    .freeStyleCropEnabled(true)//剪裁框是否可拖拽
                    .circleDimmedLayer(true)//是否圆形剪裁
                    .showCropFrame(false)//是否显示剪裁矩形边框
                    .showCropGrid(false)//是否显示剪裁矩形网格
                    .selectionMedia(list)//是否传入已选图片
                    .forResult(PictureConfig.CHOOSE_REQUEST)//结果回调onActivityResult
        }
    }
}