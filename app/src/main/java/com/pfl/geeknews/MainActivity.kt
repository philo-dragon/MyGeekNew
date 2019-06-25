package com.pfl.geeknews

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.howshea.basemodule.utils.toast
import com.pfl.lib_common.base.BaseActivity
import com.pfl.news_detail.NewsDetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MainActivity : BaseActivity() {

    private val loginViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun getContentView() = R.layout.activity_main

    override fun isImmersive() = false

    override fun isDarkMode() = false

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
        showDialog()
        loginViewModel.login("13488747197", "12345678a")
    }

    fun goDetail(view: View) {
        startActivity(Intent(this, NewsDetailActivity::class.java))
    }

}
