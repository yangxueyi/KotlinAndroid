package com.xueyi.yang.kotlinandroid.module.welcome

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.xueyi.yang.kotlinandroid.MainActivity
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.adapter.GuideViewpageAdapter
import com.xueyi.yang.kotlinandroid.base.BaseActivity
import com.xueyi.yang.kotlinandroid.constant.Constant
import com.xueyi.yang.kotlinandroid.module.welcome.contract.WelcomeContract
import com.xueyi.yang.kotlinandroid.module.welcome.presenter.WelcomePresenter
import com.xueyi.yang.kotlinandroid.utils.SpUtils
import kotlinx.android.synthetic.main.activity_welcome.*

/**
 * Created by YangXueYi
 * Time : 2018/1/17.
 */
class WelcomeActivity :BaseActivity(),WelcomeContract.view{

    /**
     * 获取Boolean值，判断是不是第一次进入
     * 调用preference的getValue
     */
    private var isFirstOpen :Boolean by SpUtils(Constant.FIRST_OPEN, true)

    // 引导页图片资源
    private val images = intArrayOf(R.layout.guide_1,R.layout.guide_2,R.layout.guide_3)
    //创建一个集合，将页面添加，传入到viewpager的适配器
    private val views = arrayListOf<View>()
    // 底部小点图片
    private val dots = arrayOfNulls<ImageView>(images.size)

    // 定义一个变量记录当前选中位置
    private var currentIndex = 0

    /*使用lazy函数与by创建一个委托，获取WelcomePresenter对象*/
    private val welcomePresenter: WelcomePresenter by lazy {
        WelcomePresenter(this)
    }

    private val guideViewpagerAdapter : GuideViewpageAdapter by lazy {
        GuideViewpageAdapter(views)
    }

    override fun initLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_welcome)
    }
    override fun init() {

        //初始化引导页面
        for (i in images.indices){
            //填充页面
            val view = LayoutInflater.from(this).inflate(images[i], null)
            if(i == images.size-1){
                //拿到最后一个页面的按钮
                val btn : Button = view.findViewById(R.id.btn_enter)
                btn.setOnClickListener(onClickListener)
            }

            views.add(view)
        }
        vp.run {//添加viewpage适配器
            adapter = guideViewpagerAdapter
            //viewpage的滚动监听
            addOnPageChangeListener(MyPageChangeListener())
        }
        //添加底部圆点
        initDots()
    }

    override fun initListener() {
    }

    override fun initAdapter() {
    }

    override fun onSkipActivity() {
        startActivity(Intent(this@WelcomeActivity,MainActivity::class.java))
        isFirstOpen = false
        finish()
    }

    private fun initDots() {
        // 循环取得小点图片
        for (i in images.indices){
            // 得到LinearLayout下面的每一个子元素
            dots[i] = ll.getChildAt(i) as ImageView
            dots[i]!!.isEnabled = false //设置为灰色，即非选中状态
        }
        dots[currentIndex]!!.isEnabled = true  // 设置为白色，即选中状态
    }
    /** onClickListener*/
    private val onClickListener = View.OnClickListener {
        view ->
        when (view.id) {
            R.id.btn_enter -> {
                welcomePresenter.skipActivity()
            }
        }
    }
    /**
     * 设置当前指示点
     * @param position
     */
     fun setCurDot(position: Int) {
        if (position < 0 || position > images.size || currentIndex == position) {
            return
        }
        dots[position]!!.isEnabled = true
        dots[currentIndex]!!.isEnabled = false
        currentIndex = position
    }

    inner class MyPageChangeListener : ViewPager.OnPageChangeListener {
        //选择页面
        override fun onPageSelected(position: Int) {
            // 设置底部小点选中状态
            setCurDot(position)
        }
        //页面滚动状态改变
        override fun onPageScrollStateChanged(state: Int) {
        }
        //页面滚动
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }
    }
}