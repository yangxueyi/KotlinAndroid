package com.xueyi.yang.kotlinandroid.module.home

import android.app.Activity
import android.app.FragmentManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout.DrawerListener
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import android.view.WindowManager
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*
import android.support.v7.widget.AppCompatButton
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia
import com.xueyi.yang.kotlinandroid.constant.Constant
import com.xueyi.yang.kotlinandroid.fragment.home.HomeFragment
import com.xueyi.yang.kotlinandroid.fragment.hot.HotFragment
import com.xueyi.yang.kotlinandroid.fragment.type.TypeFragment
import com.xueyi.yang.kotlinandroid.module.about.AboutActivity
import com.xueyi.yang.kotlinandroid.module.collection.CollectionActivity
import com.xueyi.yang.kotlinandroid.module.login.LoginActivity
import com.xueyi.yang.kotlinandroid.module.search.SearchActivity
import com.xueyi.yang.kotlinandroid.utils.PictureSelectorUtils
import com.xueyi.yang.kotlinandroid.utils.SpUtils
import com.xueyi.yang.kotlinandroid.utils.ToastUtils
import jp.wasabeef.glide.transformations.CropCircleTransformation


/**
 * Created by YangXueYi
 * Time : 2018/1/19.
 */
class HomeActivity : BaseActivity(){

    /**
     * local username
     */
    private val username: String by SpUtils(Constant.USERNAME_KEY, "")

    private var lastTime: Long = 0
    private var currentIndex = 0


    /*拿到向下兼容的fragmentManager*/
    private val fragmentManager by lazy {
        supportFragmentManager
    }
    private var homeFragment: HomeFragment? = null
    private var typeFragment: TypeFragment? = null
    private var hotFragment: HotFragment? = null

    private var isDrawer :Boolean = false

    //修剪后的图片的保存地址
    private var imagePath :String by SpUtils(Constant.IMAGE_PATH,"")
    //获取是否已经登录账号
    private var isLogin : Boolean by SpUtils(Constant.LOGIN_KEY,false)
    //获取登录账号
    private var userName : String by SpUtils(Constant.USERNAME_KEY,"")

    //选择图片的集合
    private var selectorList = listOf<LocalMedia>()

    //拿到侧滑菜单头文件的ImageView，选择图片作为头像
    private val navigationImage : ImageView by lazy {
        navigation_view.getHeaderView(0).findViewById<ImageView>(R.id.navigation_viewHead)
    }
    //拿到侧滑菜单头文件的TextView，显示登录未登录
    private val navigationViewUsername : TextView by lazy {
        navigation_view.getHeaderView(0).findViewById<TextView>(R.id.navigation_view_text)
    }
    //拿到侧滑菜单头文件的AppCompatButton，进行登录设置
    private val navigationViewLogout :AppCompatButton by lazy {
        navigation_view.getHeaderView(0).findViewById<AppCompatButton>(R.id.navigation_viewLogout)
    }

    override fun initLayout(savedInstanceState: Bundle?) {
       setContentView(R.layout.activity_home)
    }

    /*初始化toolbar*/
    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.tool_bar).init()
    }

    override fun init() {
        /*p根据保存地址判断是不是需要加载头像*/
        if (imagePath!=""){
            //加载图片
            loadImage()
        }

        //toolbar的基本设置
        tool_bar.run {
            title = getString(R.string.app_name)//标题
            setSupportActionBar(this)//给toolbar设置监听，需要将toolbar设置支持actionbar
        }
        //设置底部导航栏
        bottom_navigation.run {
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            //初始化是默认显示homeFragment
            selectedItemId = R.id.navigation_home
        }

        //drawerLayout的基本设置
        drawer_Layout.run {
            //关联drawerLayout与tool_bar
            val toggle = ActionBarDrawerToggle(this@HomeActivity, this, tool_bar,R.string.navigation_drawer_open, R.string.navigation_drawer_close)
            addDrawerListener(toggle)
            toggle.syncState()
            //仿qq侧滑菜单
            addDrawerListener(DrawerListener)
        }
        //设置头像的更换
        navigationImage.run {
            setOnClickListener{
                /*图片的选择*/
                PictureSelectorUtils.select(this@HomeActivity,selectorList)
            }
        }
        //设置显示是否已经登录
        navigationViewUsername.run {
            text = if (!isLogin){
                getString(R.string.not_login)
            }else{
                userName
            }
        }
        //设置登录按钮
        navigationViewLogout.run {
            text = if(!isLogin){
                getString(R.string.please_login)
            }else{
                getString(R.string.out_login)
            }
            setOnClickListener{
                if(!isLogin){//未登录
                    //跳转到登录界面
                    val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                    startActivityForResult(intent,Constant.MAIN_REQUEST_CODE)
                }else{//已登录
                    SpUtils.clear()//退出登录
                    navigationViewUsername.text = getString(R.string.not_login)
                    text = getString(R.string.please_login)
                    homeFragment?.refreshData()

                }
            }
        }

        /*侧滑菜单上面的menu*/
        navigation_view.run {
            setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
        }
    }


    override fun initAdapter() {
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)//加载menu文件到布局
        return super.onCreateOptionsMenu(menu)
    }

    /*设置toolbar上面的菜单*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menuHot ->{
                if (currentIndex == R.id.menuHot){
                    hotFragment?.refreshData()
                }
                setFragment(R.id.menuHot)
                currentIndex = R.id.menuHot
                return true
            }
            R.id.menuSearch ->{
                Intent(this,SearchActivity::class.java).run {
                    startActivity(this)
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onShowToast(str: String) {
        ToastUtils.toast(this,str)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST){
            val list = PictureSelector.obtainMultipleResult(data)
            list.forEach {
                //保存头像地址
                imagePath = it.compressPath
                /*加载图片*/
                loadImage()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // other activity login
        if (isLogin && navigationViewUsername.text.toString() != username) {
            navigationViewUsername.text = username
            navigationViewLogout.text = getString(R.string.logout)
            homeFragment?.refreshData()
        }
    }
    /**
     * 退出按钮
     */
    override fun onBackPressed() {
        //判断侧滑菜单是否是开启的，如果是关闭
        if(drawer_Layout.isDrawerOpen(GravityCompat.START)){
            drawer_Layout.closeDrawer(GravityCompat.START)
            return
        }
        /*按两次退出程序*/
        val millis = System.currentTimeMillis()//获取点击的时间
        //如果两次点击的时间小于2秒就退出程序
        if (millis - lastTime <= 2*1000){
            super.onBackPressed()
            finish()
        }else{
            onShowToast(getString(R.string.double_click_exit))
            lastTime = millis//将点击时间赋值给开始时间
        }
    }
    /*实现DrawerListener，修改右边布局*/
    private val DrawerListener = object :DrawerListener{
        override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
            isDrawer = true
            //获取屏幕的宽高
            val windowManger = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = windowManger.defaultDisplay
            //设置右面的布局位置  根据左面菜单的right作为右面布局的left
            // 左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
            main_linear?.layout(navigation_view?.right!!,0, navigation_view?.right!! +display.width,display.height)
        }
        override fun onDrawerStateChanged(newState: Int) {
        }
        override fun onDrawerClosed(drawerView: View?) {
            isDrawer=false
        }
        override fun onDrawerOpened(drawerView: View?) {
        }

    }

    /**
     * 防止重叠
     */
    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is HomeFragment -> homeFragment ?: let { homeFragment = fragment }
            is TypeFragment -> typeFragment ?: let { typeFragment = fragment }
            is HotFragment -> hotFragment ?: let { hotFragment = fragment }
        }
    }

    /**
     * 显示对应Fragment
     */
    private fun setFragment(index: Int) {
        //判断侧滑菜单是否是开启的，如果是关闭
        if(drawer_Layout.isDrawerOpen(GravityCompat.START)){
            drawer_Layout.closeDrawer(GravityCompat.START)
        }
        fragmentManager.beginTransaction().apply {
            //创建fragment
            homeFragment?:let {//“?:let”homeFragment为空，创建fragment
                HomeFragment().let {
                    homeFragment = it
                    add(R.id.content,it)
                }
            }
            typeFragment?:let {
                TypeFragment().let {
                    typeFragment = it
                    add(R.id.content,it)
                }
            }
            hotFragment?:let {
                HotFragment().let {
                    hotFragment = it
                    add(R.id.content,it)
                }
            }

            //先将创建好的fragment隐藏,
            hideFragment(this)
            //显示fragment
            when (index) {
                R.id.navigation_home -> {
                    tool_bar.title = getString(R.string.start)
                    homeFragment?.let {
                        this.show(it)
                    }
                }
                R.id.navigation_knowledge ->{
                    tool_bar.title = getString(R.string.title_knowledge)
                    typeFragment?.let {
                        this.show(it)
                    }
                }
                R.id.menuHot ->{
                    tool_bar.title = getString(R.string.hot_title)
                    hotFragment?.let {
                        this.show(it)
                    }
                }
            }
        }.commit()
    }

    /**
     * 隐藏所有fragment
     */
    private fun hideFragment(transaction: FragmentTransaction) {
        homeFragment?.let {//HomeFragment不为空，执行lambda；为空，就什么都不执行
            transaction.hide(it)
        }
        typeFragment?.let {
            transaction.hide(it)
        }
        hotFragment?.let {
            transaction.hide(it)
        }
    }

    private fun loadImage(){
        Glide.with(this)
                .load(imagePath)//it.compressPath :压缩后的图片的path地址
                .apply(RequestOptions.bitmapTransform(CropCircleTransformation()))//将图片转换为圆形
                .into(navigationImage)
    }

    private val onNavigationItemSelectedListener = OnNavigationItemSelectedListener{
        item ->  setFragment(item.itemId)
        return@OnNavigationItemSelectedListener when (item.itemId) {
            /*判断点击的是哪个button*/
            R.id.navigation_home -> {
                //如果点击的是自己就回到顶部
                if (currentIndex == R.id.navigation_home) {
                    homeFragment?.smoothScrollToPosition()
                }
                currentIndex = R.id.navigation_home
                true
            }
            R.id.navigation_knowledge ->{
                if (currentIndex == R.id.navigation_knowledge){
                    typeFragment?.smoothScrollToPosition()
                }
                currentIndex = R.id.navigation_knowledge
                true
            }
            else -> {
                false
            }
        }
    }

    private val onDrawerNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener{
        item ->
        when(item.itemId){
            R.id.my_like ->{
                if(!isLogin){//判断如果没登录，先登录
                    Intent(this,LoginActivity::class.java).run {
                        startActivityForResult(this, Constant.MAIN_REQUEST_CODE)
                    }
                    onShowToast(getString(R.string.login_please_login))
                    return@OnNavigationItemSelectedListener true
                }
                //跳转到收藏界面
                Intent(this, CollectionActivity::class.java).run {
                    putExtra(Constant.SEARCH_KEY,false)
                    startActivityForResult(this, Constant.MAIN_LIKE_REQUEST_CODE)
                }
            }
            R.id.about_us ->{
                Intent(this,AboutActivity::class.java).run {
                    startActivity(this)
                }
            }
        }

        drawer_Layout.closeDrawer(GravityCompat.START)
        true
    }


}