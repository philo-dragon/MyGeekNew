package com.pfl.news_detail

import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.pfl.geeknews.R
import com.pfl.lib_common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_nested_scroll.*

class NestedScrollActivity : BaseActivity() {

    override fun getContentView() = R.layout.activity_nested_scroll

    override fun isImmersive() = false

    override fun isDrakMode() = false

    override fun initView() {
        initWebView()
    }

    private fun initWebView() {
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        webView.loadUrl("https://github.com/wangzhengyi/Android-NestedDetail")
    }
}
