package com.xueyi.yang.kotlinandroid.bean

/**
 * Created by YangXueYi
 * Time : 2018/1/31.
 */

data class HotKeyResponse(
		val data: List<Data>,
		val errorCode: Int, //0
		val errorMsg: String
){
    data class Data(
            val id: Int, //6
            val link: String,
            val name: String, //面试
            val order: Int, //1
            val visible: Int //1
    )
}