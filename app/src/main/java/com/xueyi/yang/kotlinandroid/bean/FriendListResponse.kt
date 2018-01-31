package com.xueyi.yang.kotlinandroid.bean

/**
 * Created by YangXueYi
 * Time : 2018/1/31.
 */

data class FriendListResponse(
		val data: List<Data>,
		val errorCode: Int, //0
		val errorMsg: String
){
    data class Data(
            val icon: String,
            val id: Int, //17
            val link: String, //http://www.wanandroid.com/article/list/0?cid=176
            val name: String, //国内大牛博客集合
            val order: Int, //1
            val visible: Int //1
    )
}