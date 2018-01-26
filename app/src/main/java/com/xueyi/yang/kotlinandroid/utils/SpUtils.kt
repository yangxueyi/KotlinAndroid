package com.xueyi.yang.kotlinandroid.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.xueyi.yang.kotlinandroid.constant.Constant
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by YangXueYi
 * Time : 2018/1/18.
 */
class SpUtils <T>(private val name: String, private val default: T): ReadWriteProperty<Any?,T>{

    /*
    *companion object定义的成员类似于Java中的静态成员，
    * 因为kotlin中没有static成员
    *
    */
    companion object {
        lateinit var sp : SharedPreferences
        /**
         * init Context
         * @param context Context
         */
        fun setContext(context : Context){
           sp = context.getSharedPreferences(context.packageName + Constant.SHARED_NAME, Context.MODE_PRIVATE)
        }

        fun clear(){
            sp.edit().clear().apply()
        }

    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T  = findPreference(name,default)

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T)  {
        putPreference(name,value)
    }

    /*
    * 获取值
    * */
    @Suppress("UNCHECKED_CAST")
    private fun<U> findPreference(name: String, default: U): U = with(sp){

        var res : Any = when(default){
            is String -> getString(name,default) //等同于sp.getString   or   this.getString
            is Long -> getLong(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }
        //最后将得到的值转换为你需要的类型
        res as U
    }

/*
* 设置值
* */
@SuppressLint("CommitPrefEdits")
    private fun<U> putPreference(name: String, value: U) = with(sp.edit()){
        when(value){
            is String -> putString(name, value)//等同于sp.putString   or   this.putString
            is Long -> putLong(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }.apply()//提交一下，等同于commit(),但是apply（）效率更高
    }

}