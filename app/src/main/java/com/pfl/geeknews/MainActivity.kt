package com.pfl.geeknews

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.howshea.basemodule.utils.toast
import com.pfl.lib_common.base.BaseActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private val loginViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun getContentView() = R.layout.activity_main

    override fun isImmersive() = false

    override fun isDarkMode() = false

    override fun initData(savedInstanceState: Bundle?) {
        startObserve()
        initWebView()
        initRecyclerView()
    }

    private fun initWebView() {
        webContainer.settings.javaScriptEnabled = true
        webContainer.webViewClient = WebViewClient()
        webContainer.webChromeClient = WebChromeClient()
        webContainer.loadUrl("https://github.com/wangzhengyi/Android-NestedDetail")
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        rvList.layoutManager = layoutManager
        val data = getCommentData()
        val rvAdapter = RvAdapter()
        rvList.adapter = rvAdapter
        rvAdapter.setNewData(data)
    }

    private fun getCommentData(): List<InfoBean> {
        val commentList = ArrayList<InfoBean>()
        val titleBean = InfoBean()
        titleBean.type = InfoBean.TYPE_TITLE
        titleBean.title = "评论列表"
        commentList.add(titleBean)
        for (i in 0..39) {
            val contentBean = InfoBean()
            contentBean.type = InfoBean.TYPE_ITEM
            contentBean.title = "评论标题$i"
            contentBean.content = "评论内容$i"
            commentList.add(contentBean)
        }
        return commentList
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

    fun requestData() {
        showDialog()
        loginViewModel.login("13488747197", "12345678a")
    }


}
