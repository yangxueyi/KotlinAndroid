package com.xueyi.yang.kotlinandroid.retrofit

import com.xueyi.yang.kotlinandroid.bean.ArticleListResponse
import com.xueyi.yang.kotlinandroid.bean.BannerResponse
import com.xueyi.yang.kotlinandroid.bean.HomeListResponse
import com.xueyi.yang.kotlinandroid.bean.TreeListResponse
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.*

/**
 * Created by YangXueYi
 * Time : 2018/1/24.
 */
interface RetrofitService {
    /*
    *首页数据:
    * http://www.wanandroid.com/article/list/0/json
    */
    @GET("/article/list/{page}/json")
    fun getHomeList(@Path("page") page : Int) : Deferred<HomeListResponse>

    /*
     * 首页Banner
     * http://www.wanandroid.com/banner/json
     * @return BannerResponse
     */
    @GET("/banner/json")
    fun getBannerList() : Deferred<BannerResponse>

    /*
    * 知识体系
    * http://www.wanandroid.com/tree/json
    */
    @GET("/tree/json")
    fun getTypeTreeList() : Deferred<TreeListResponse>
    /*
   * 知识体系下的文章
   * http://www.wanandroid.com/article/list/0/json?cid=168
   */
    @GET("/article/list/{page}/json")
    fun getArticleList(@Path("page")page: Int,
                        @Query("cid")cid:Int):Deferred<ArticleListResponse>


}