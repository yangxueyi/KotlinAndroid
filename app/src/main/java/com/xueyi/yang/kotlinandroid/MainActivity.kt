package com.xueyi.yang.kotlinandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(lay)
        println(lay)
    }

    private val lay : String by lazy {
        println("123")
        "3210"
    }
}
