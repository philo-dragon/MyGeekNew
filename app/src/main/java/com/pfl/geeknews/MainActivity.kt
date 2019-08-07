package com.pfl.geeknews

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.howshea.basemodule.utils.toast
import com.pfl.lib_common.base.BaseActivity
import com.pfl.lib_common.extentions.statisticsMethodRunTime
import com.pfl.news_detail.NestedScrollActivity
import com.pfl.floating.FloatViewService


class MainActivity : BaseActivity() {

    private val loginViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun isImmersive() = false

    override fun isDrakMode() = false

    override fun backgroundColorRes() = R.color.background

    override fun getContentView() = R.layout.activity_main

    override fun initData(savedInstanceState: Bundle?) {
        startObserve()
    }

    private fun startObserve() {
        loginViewModel.apply {

            loginLiveData.observe(this@MainActivity, Observer {
                dismissDialog()
                toast("请求成功")
            })

            dailyLiveData.observe(this@MainActivity, Observer {
                dismissDialog()
                toast("请求成功")
            })

            getErrorObserve().observe(this@MainActivity, Observer {
                dismissDialog()
                toast(it.message)
            })
        }
    }

    fun requestData(view: View) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else {
                statisticsMethodRunTime { showDialog() }
                loginViewModel.login("13488747197", "12345678a")
                val intent = Intent(this@MainActivity, FloatViewService::class.java)
                startService(intent)
            }


        }

        fun goDetail(view: View) {
            startActivity(Intent(this, NestedScrollActivity::class.java))
        }

    }
}
