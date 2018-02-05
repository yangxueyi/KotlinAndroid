package com.xueyi.yang.kotlinandroid.module.homeContent

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.ChromeClientCallbackManager
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.base.BaseActivity
import com.xueyi.yang.kotlinandroid.constant.Constant
import com.xueyi.yang.kotlinandroid.getAgentWeb
import com.xueyi.yang.kotlinandroid.inflater
import kotlinx.android.synthetic.main.activity_collection.*
import kotlinx.android.synthetic.main.activity_home_content.*


/**
 * Created by YangXueYi
 * Time : 2018/1/29.
 */
class HomeContentActivity : BaseActivity() {


    private var shareId : Int= 0
    private lateinit var shareTitle : String
    private lateinit var shareUrl : String
    private lateinit var agentWeb: AgentWeb


    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.tool_bar).init()
    }

    override fun initLayout(savedInstanceState: Bundle?) {
       setContentView( R.layout.activity_home_content)
    }

    override fun init() {
        //toolbar的基本设置
        tool_bar.run {
            title = getString(R.string.loading)//正在加载标题
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        //获取从homefragment传过来的信息
        intent.extras?.let {
            shareId = it.getInt(Constant.CONTENT_ID_KEY)
            shareTitle = it.getString(Constant.CONTENT_TITLE_KEY)
            shareUrl = it.getString(Constant.CONTENT_URL_KEY)
            //获取agentweb
            agentWeb = shareUrl.getAgentWeb(this,web_Content,LinearLayout.LayoutParams(-1,-1),toolbarTitleCallback)

        }

    }

    override fun initAdapter() {
    }

    /*加载toolbar上 的menu*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.web_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    //toolbar 上的menu的选择
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home ->{
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        //开启web
        agentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    /*谷歌管理的title的回调*/
    private val toolbarTitleCallback = ChromeClientCallbackManager.ReceivedTitleCallback{
        _, title ->
        title?.let { //设置加载内容后的标题
            tool_bar.title = it
        }
    }


}