package com.xueyi.yang.kotlinandroid.base

import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.xueyi.yang.kotlinandroid.R
import com.xueyi.yang.kotlinandroid.base.contract.BaseContract

/**
 * Created by YangXueYi
 * Time : 2018/1/17.
 */
abstract class BaseActivity : AppCompatActivity(), BaseContract.BaseView {


    /*code值*/
    protected  val PERMISSIONS_REQUEST_CODE : Int = 254
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout(savedInstanceState)

        init()
        initListener()
        initAdapter()

    }

    /*初始化布局*/
    protected abstract fun initLayout(savedInstanceState: Bundle?)
    /*初始化监听器*/
    abstract fun initListener()
    /*适配器*/
    abstract fun initAdapter()
    /*初始化*/
    abstract fun init()


    override fun onShowToast(str : String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onShowSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onShowError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*检查敏感权限*/
    protected  fun  checkPermissions(permission:  Array<String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){//M是Android6.0
            return true
        }
        permission.forEach {
            if (ContextCompat.checkSelfPermission(applicationContext,it)!=PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    /*申请动态权限*/
    protected fun requestPermission(permission: Array<String>, resultCode : Int){
        if(aginRequestPermission(permission)){
            showPermissionDialog(permission,resultCode)
        }else{
            ActivityCompat.requestPermissions(this,permission,resultCode)
        }
    }

    /*再次申请权限*/
    private fun aginRequestPermission(permission: Array<String>): Boolean {
        permission.forEach {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,it))
                return true
        }
        return false
    }

    /*检查回调结果权限组*/
    protected fun checkPermissionsGranted(grantResults : IntArray) : Boolean{
        grantResults.forEach {
            if(it!=PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    /*再次申请权限时的对话框*/
    private fun showPermissionDialog(permission: Array<String>, resultCode: Int) {
        AlertDialog.Builder(this)
                .setTitle(R.string.permissionTitle)
                .setMessage(R.string.permissionMessage)
                //取消
                .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener {
                    dialogInterface, i -> finish()//关闭程序

                })
                //重新申请
                .setPositiveButton(R.string.reseting, DialogInterface.OnClickListener {
                    dialogInterface, i ->  ActivityCompat.requestPermissions(this,permission,resultCode)
                })
                .setCancelable(false)
                .show()
    }
}
