package com.xueyi.yang.kotlinandroid.module.typeContent

import android.os.Bundle
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.base.BaseActivity
import com.xueyi.yang.kotlinandroid.constant.Constant
import kotlinx.android.synthetic.main.activity_type_content.*

/**
 * Created by YangXueYi
 * Time : 2018/1/29.
 */
class TypeContentActivity : BaseActivity() {
    /*
    *判断是从那个界面传过来的
    */
    private var target : Boolean = false
    /*toolbar 的title*/
    private lateinit var  typeTitle : String



    override fun initLayout(savedInstanceState: Bundle?) {
       setContentView(R.layout.activity_type_content)
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.type_content_toolbar).init()
    }

    override fun init() {

        //toolbar的基本设置
        type_content_toolbar.run {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        //传过来的信息
        intent.extras?.let {
            target = it.getBoolean(Constant.CONTENT_TARGET_KEY,false)
            it.getString(Constant.CONTENT_TITLE_KEY)?.let {
                typeTitle = it
                type_content_toolbar.title = it
            }

        }
    }

    override fun initAdapter() {
    }

}