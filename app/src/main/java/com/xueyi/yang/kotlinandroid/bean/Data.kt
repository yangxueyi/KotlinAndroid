package com.xueyi.yang.kotlinandroid.bean

/**
 * Created by YangXueYi
 * Time : 2018/1/24.
 */
data class Data(
        val datas: List<HomeDatas>,
        val offset: Int, //0
        val size: Int, //20
        val total: Int, //1009
        val pageCount: Int, //51
        val curPage: Int, //1
        val over: Boolean //false


)