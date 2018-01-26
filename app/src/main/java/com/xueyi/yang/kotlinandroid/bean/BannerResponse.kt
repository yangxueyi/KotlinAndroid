package com.xueyi.yang.kotlinandroid.bean

/**
 * Created by YangXueYi
 * Time : 2018/1/25.
 */
data class BannerResponse (
        val errorCode: Int, //0
        val errorMsg: Any, //null
        val data: List<Data>){
    data class Data(
            val id: Int, //6
            val url: String, //http://www.wanandroid.com/navi
            val imagePath: String, //http://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png
            val title: String, //我们新增了一个常用导航Tab~
            val desc: String,
            val isVisible: Int, //1
            val order: Int, //1
            val type: Int //0
    )
}