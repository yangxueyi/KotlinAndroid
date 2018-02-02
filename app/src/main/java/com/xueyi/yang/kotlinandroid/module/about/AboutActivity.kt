package com.xueyi.yang.kotlinandroid.module.about

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.MenuItem
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.content_about.*


/**
 * Created by YangXueYi
 * Time : 2018/2/1.
 */
class AboutActivity :BaseActivity() {

    /*初始化toolbar*/
    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.tool_bar).init()
    }
    override fun initLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_about)
    }
    @Suppress("DEPRECATION")
    override fun init() {
        tool_bar.run {
            title = getString(R.string.my_about)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)//显示返回按钮
        }

        tv_about_version.text = getString(R.string.version_code,getString(R.string.app_name),
                packageManager.getPackageInfo(packageName,0).versionName)
        tv_about_content.run {
            text = Html.fromHtml(getString(R.string.about_content))//转变为HTML格式
            movementMethod = LinkMovementMethod.getInstance()//设置网址可点击跳转到浏览器

        }
    }

    override fun initAdapter() {
    }

    /*设置返回键*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}