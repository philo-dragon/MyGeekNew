package com.pfl.lib_common.base

import android.os.Bundle
import android.os.PersistableBundle
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

    /**
     * @return 窗口默认背景颜色
     */
    var backgroundColorRes: Int = R.color.background

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
    fun initListener() {}

    private fun setContentView() {
        window.setBackgroundDrawableResource(backgroundColorRes)
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