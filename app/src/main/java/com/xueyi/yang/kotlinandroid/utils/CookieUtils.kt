package com.xueyi.yang.kotlinandroid.utils

/**
 * Created by YangXueYi
 * Time : 2018/1/24.
 */
class CookieUtils {
    /**
     * 加密
     * */
    companion object {
        /*压缩保存cookie*/
        fun encodeCookie(cookies: List<String>) : String{
            val sb = StringBuilder()
            val set = HashSet<String>()
            /*将cookies集合转换为一个新的集合    map
            *
            * dropLastWhile:返回从最后一项起，去掉满足条件的元素，直到不满足条件的一项为止；
            * filterNot:过滤掉所有满足条件的元素
            * */
            cookies.map {
                cookie -> cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()}
                    .forEach {it.filterNot {set.contains(it)}.forEach {set.add(it)}
                    }

                //获取迭代器
                val iterator = set.iterator()
                while (iterator.hasNext()){
                    val cookie = iterator.next()
                    sb.append(cookie).append(";")//将每个cookie保存到sb
                }
                //判断最后一位是否是";"如果是,删除
                val last = sb.lastIndexOf(";")
                if (sb.length-1 == last){
                    sb.deleteCharAt(last)
                }
                return sb.toString()
            }


        /*保存coolie到sp*/
        @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
        fun saveCookie(url : String? , domain : String? , cookie: String){
                url ?: return
                var spUrl : String by SpUtils(url,cookie)
                @Suppress("UNUSED_VALUE")
                spUrl = cookie
                domain ?: return
                var spDomain : String by SpUtils(domain,cookie)
                @Suppress("UNUSED_VALUE")
                spDomain = cookie
            }
        }

}