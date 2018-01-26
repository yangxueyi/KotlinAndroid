package com.xueyi.yang.kotlinandroid.bean

import java.io.Serializable

/**
 * Created by YangXueYi
 * Time : 2018/1/24.
 */
data class TreeListResponse (
		val errorCode: Int, //0
		val errorMsg: Any, //null
		val data: List<Data>) {
    data class Data(
            val id: Int, //150
            val name: String, //开发环境
            val courseId: Int, //13
            val parentChapterId: Int, //0
            val order: Double, //1.0
            val visible: Int, //1
            val children: List<Children>) : Serializable {
        data class Children(
                val id: Int, //60
                val name: String, //Android Studio相关
                val courseId: Int, //13
                val parentChapterId: Int, //150
                val order: Double, //1000.0
                val visible: Int, //1
                val children: List<Any> ) : Serializable
    }
}