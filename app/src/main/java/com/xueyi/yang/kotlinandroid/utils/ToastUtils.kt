package com.xueyi.yang.kotlinandroid.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by YangXueYi
 * Time : 2018/1/23.
 */
class ToastUtils {
    companion object {
         fun toast(context: Context,string: String){
            Toast.makeText(context,string,Toast.LENGTH_SHORT).show()
        }
    }


}