package com.pfl.lib_common.base

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.jojo.design.common_ui.dialog.LoadingDialog
import com.pfl.lib_common.R
import com.pfl.lib_common.listener.IActivity
import com.pfl.lib_common.utils.StatusBarUtil


/**
 * Activity 基类 设置公用的方法,属性
 */
abstract class BaseActivity : AppCompatActivity(), IActivity, LifecycleOwner {

    lateinit var mContext: AppCompatActivity
    private val mLifecycleRegistry = LifecycleRegistry(this)

    //用来控制应用前后台切换的逻辑
    private var isCurrentRunningForeground = true

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mContext = this
        setContentView()
        immersive()
        darkMode()
        initData(savedInstanceState)
        initView()
        initListener()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        removeFragmentCache(outState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        removeFragmentCache(outState)
    }

    private fun removeFragmentCache(outState: Bundle?) {
        outState?.apply {
            val fragmentTag = "android:support:fragments"
            outState.remove(fragmentTag)
        }
    }

    override fun onStart() {
        super.onStart()
        if (!isCurrentRunningForeground) {
            isCurrentRunningForeground = true
            //处理跳转到广告页逻辑
            //val intennt = Intent(this, LoadingActivity::class.java)
            //startActivity(intennt)
            Log.e("BaseActivity", ">>>>>>>>>>>>>>>>>>>切回前台 activity process")
        }
    }

    override fun onStop() {
        super.onStop()
        isCurrentRunningForeground = isRunningForeground()
        if (!isCurrentRunningForeground) {
            Log.e("BaseActivity", ">>>>>>>>>>>>>>>>>>>切到后台 activity process")
        }
    }

    private fun isRunningForeground(): Boolean {
        val activityManager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcessInfos = activityManager.runningAppProcesses
        // 枚举进程,查看该应用是否在运行
        for (appProcessInfo in appProcessInfos) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName == this.applicationInfo.processName) {
                    Log.e("BaseActivity", "EntryActivity isRunningForeGround")
                    return true
                }
            }
        }
        Log.e("BaseActivity", "EntryActivity isRunningBackGround")
        return false
    }

    /**
     * 初始化使用：
     *
     *
     * 沉浸式
     */
    private fun immersive() {
        if (isImmersive) {
            StatusBarUtil.immersive(this)
        }
    }

    /**
     * 初始化使用：
     *
     *
     * 设置深色模式
     */
    private fun darkMode() {
        doDrakMode(isDrakMode())
    }

    /**
     * 设置深色模式
     * 给子类使用可以动态设置深色或浅色模式
     */
    open fun doDrakMode(isDarkMode: Boolean) {
        StatusBarUtil.darkMode(this, isDarkMode)
    }

    /**
     * @return 是否沉浸式
     */
    override fun isImmersive(): Boolean {
        return true
    }

    /**
     * @return 是否沉浸式
     */
    open fun isDrakMode(): Boolean {
        return true
    }

    /**
     * @return 窗口默认背景颜色
     */
    open fun backgroundColorRes() = R.color.background

    /**
     * @param color 状态栏背景颜色
     * @param alpha 透明度
     */
    open fun setStatusBarBgColor(color: Int, alpha: Float) {
        StatusBarUtil.immersive(this, color, alpha)
    }

    /**
     * 初始化数据
     */
    override fun initData(savedInstanceState: Bundle?) {}

    /**
     * 初始化view
     */
    override fun initView() {}

    /**
     * 初始化监听
     */
    open fun initListener() {}

    private fun setContentView() {
        window.setBackgroundDrawableResource(backgroundColorRes())
        contentView = contentView
    }

    /**********************  dialog  ********************/

    private var mDialog: LoadingDialog? = null

    fun showDialog() {
        if (mDialog == null || !mDialog!!.isShowing()) {
            mDialog = LoadingDialog(this)
            mDialog!!.show()
        }
    }

    fun dismissDialog() {
        if (null != mDialog && mDialog!!.isShowing()) {
            mDialog!!.dismiss()
        }
    }

}