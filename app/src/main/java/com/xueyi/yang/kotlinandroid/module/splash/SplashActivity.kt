package com.xueyi.yang.kotlinandroid.module.splash

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.SystemClock
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.base.BaseActivity
import com.xueyi.yang.kotlinandroid.constant.Constant
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*
import java.util.concurrent.TimeUnit
import android.content.Intent
import com.xueyi.yang.kotlinandroid.MainActivity
import com.xueyi.yang.kotlinandroid.module.welcome.WelcomeActivity
import com.xueyi.yang.kotlinandroid.utils.SpUtils


/**
 * Created by YangXueYi
 * Time : 2017/12/11.
 */
class SplashActivity : BaseActivity() {

   /**
    * 获取Boolean值，判断是不是第一次进入
    * 调用preference的getValue
    */
    private var isFirstOpen :Boolean by SpUtils(Constant.FIRST_OPEN, true)


    //图片的集合，随机产生一张图片，进行动画
    private val Images = arrayOf(
            R.drawable.welcomimg1,R.drawable.welcomimg2,
            R.drawable.welcomimg3,R.drawable.welcomimg4,
            R.drawable.welcomimg5, R.drawable.welcomimg6,
            R.drawable.welcomimg7,R.drawable.welcomimg8,
            R.drawable.welcomimg9,R.drawable.welcomimg10,
            R.drawable.welcomimg11,R.drawable.welcomimg12)

    override fun initLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_splash)
        inspectPermissionsfun()

    }
    override fun init() {
    }
    override fun initListener() {
    }
    override fun initAdapter() {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(checkPermissionsGranted(grantResults)){//权限全部开启
            judgeIsFirstOpen()
        }else{ //申请权限
            requestPermission(permissions ,PERMISSIONS_REQUEST_CODE)
        }
    }

    /*检查权限*/
    private fun inspectPermissionsfun(){
        //检查权限
        var permissions  = arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        //在未加载的时候先判断权限是否已经添加
        val isGranted = checkPermissions(permissions)
        if(!isGranted){
            //申请权限
            requestPermission(permissions,PERMISSIONS_REQUEST_CODE)
            return
        }else{
            judgeIsFirstOpen()
        }
    }

    /*开始时的图片显示*/
    private fun randomImage(){
        val random = Random(SystemClock.elapsedRealtime())//SystemClock.elapsedRealtime()：从开始到现在的毫秒数
        iv_entry.setImageResource(Images[random.nextInt(Images.size)])//随机设置一张图片
        Observable.timer(1000,TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({startAnim()})
    }

    /*动画*/
    private fun startAnim(){
        val animatorX = ObjectAnimator.ofFloat(iv_entry, Constant.SCALEX, Constant.SCALE_START, Constant.SCALE_END)
        val animatorY = ObjectAnimator.ofFloat(iv_entry, Constant.SCALEY, Constant.SCALE_START, Constant.SCALE_END)
        AnimatorSet().apply {
            setDuration(Constant.ANIM_TIME).play(animatorX).with(animatorY)
            start()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {//动画结束后的监听
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            })
        }
    }

    /*判读是不是第一次进入程序*/
    private fun judgeIsFirstOpen() {

        //如果是第一次启动，先进入引导界面
        if(isFirstOpen){
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
            return
        }else
        //如果不是第一次启动，则正常显示
            randomImage()

    }


}
